package it.univr.musiclovers.model;

import it.univr.musiclovers.model.beans.ProductBean;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Marian Solomon
 */
public abstract class ProductModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public static void editProduct(ProductBean productBean) throws SQLException {
        String query = "UPDATE " + getTablePrefix() + "_product "
                + "SET status = ?, online = ?, weight = ?, price = ?, name = ?, "
                + "description = ?, inexpensive = ?, professional = ?, "
                + "for_child = ?, used = ?, min_age = ?, brand_id = ? "
                + "WHERE id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setBoolean(1, productBean.isStatus());
            prepareStatement.setBoolean(2, productBean.isOnline());
            prepareStatement.setFloat(3, productBean.getWeight());
            prepareStatement.setFloat(4, productBean.getPrice());
            prepareStatement.setString(5, productBean.getName());
            prepareStatement.setString(6, productBean.getDescription());
            prepareStatement.setBoolean(7, productBean.isInexpensive());
            prepareStatement.setBoolean(8, productBean.isProfessional());
            prepareStatement.setBoolean(9, productBean.isForChild());
            prepareStatement.setBoolean(10, productBean.isUsed());
            prepareStatement.setInt(11, productBean.getMinAge());
            prepareStatement.setInt(12, productBean.getBrand().getId());
            prepareStatement.setInt(13, productBean.getId());
            prepareStatement.execute();
        }
        removeProductImages(productBean.getId());
        insertProductImages(productBean);
    }

    public static List<ProductBean> getOnlineProducts() throws SQLException {
        ArrayList<ProductBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + getTablePrefix() + "_PRODUCT WHERE online = 'true'";
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    result.add(makeProduct(resultSet));
                }
            }
        }
        return result;
    }

    public static ProductBean getProduct(int productID) throws SQLException {
        ProductBean result = new ProductBean();
        String query = "SELECT * FROM " + getTablePrefix() + "_PRODUCT WHERE id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setInt(1, productID);
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = (makeProduct(resultSet));
                }
            }
        }
        return result;
    }

    public static List<String> getProductImages(int productID) throws SQLException {
        LinkedList<String> result = new LinkedList<>();
        String query = "SELECT image FROM " + getTablePrefix() + "_product_images WHERE product_id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setInt(1, productID);
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(resultSet.getString("image"));
                }
            }
        }
        return result;
    }

    public static List<ProductBean> getProducts(Map<String, Boolean> filters) throws SQLException {
        ArrayList<ProductBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + getTablePrefix() + "_product ";
        if (filters.size() > 0) {
            query += "WHERE ";
            for (Iterator<Entry<String, Boolean>> it = filters.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Boolean> entry = it.next();
                query += entry.getKey() + " = ? ";
                if (it.hasNext()) {
                    query += "AND ";
                }
            }
            query += "ORDER BY id ASC";
        }
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            int i = 0;
            for (Entry<String, Boolean> entry : filters.entrySet()) {
                preparedStatement.setBoolean(++i, entry.getValue());
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(makeProduct(resultSet));
                }
            }
        }
        return result;
    }

    public static void insertProduct(ProductBean productBean) throws SQLException {
        String query = "INSERT INTO " + getTablePrefix() + "_product "
                + "(status, online, weight, price, name, description, inexpensive, "
                + "professional, for_child, used, min_age, brand_id) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setBoolean(1, productBean.isStatus());
            prepareStatement.setBoolean(2, productBean.isOnline());
            prepareStatement.setFloat(3, productBean.getWeight());
            prepareStatement.setFloat(4, productBean.getPrice());
            prepareStatement.setString(5, productBean.getName());
            prepareStatement.setString(6, productBean.getDescription());
            prepareStatement.setBoolean(7, productBean.isInexpensive());
            prepareStatement.setBoolean(8, productBean.isProfessional());
            prepareStatement.setBoolean(9, productBean.isForChild());
            prepareStatement.setBoolean(10, productBean.isUsed());
            prepareStatement.setInt(11, productBean.getMinAge());
            prepareStatement.setInt(12, productBean.getBrand().getId());
            int affectedRows = prepareStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = prepareStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    productBean.setId(generatedKeys.getInt("id"));
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
            insertProductImages(productBean);
        }
    }

    public static void removeProduct(int productID) throws SQLException {
        removeProductImages(productID);
        String query = "DELETE FROM " + getTablePrefix() + "_PRODUCT WHERE id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setInt(1, productID);
            prepareStatement.execute();
        }
    }

    private static void insertProductImages(ProductBean productBean) throws SQLException {
        for (String image : productBean.getProductImages()) {
            String query = "INSERT INTO " + getTablePrefix() + "_product_images (image, product_id) VALUES (?,?)";
            try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
                prepareStatement.setString(1, image);
                prepareStatement.setInt(2, productBean.getId());
                prepareStatement.execute();
            }
        }
    }

    private static ProductBean makeProduct(ResultSet resultSet) throws SQLException {
        ProductBean productBean = new ProductBean();
        productBean.setId(resultSet.getInt("id"));
        productBean.setStatus(resultSet.getBoolean("status"));
        productBean.setOnline(resultSet.getBoolean("online"));
        productBean.setInexpensive(resultSet.getBoolean("inexpensive"));
        productBean.setWeight(resultSet.getFloat("weight"));
        productBean.setPrice(resultSet.getFloat("price"));
        productBean.setName(resultSet.getString("name"));
        productBean.setDescription(resultSet.getString("description"));
        productBean.setForChild(resultSet.getBoolean("for_child"));
        productBean.setMinAge(resultSet.getInt("min_age"));
        productBean.setProfessional(resultSet.getBoolean("professional"));
        productBean.setUsed(resultSet.getBoolean("used"));
        productBean.setBrand(BrandModel.getBrand(resultSet.getInt("brand_id")));
        productBean.setProductImage(getProductImages(productBean.getId()));
        return productBean;
    }

    private static void removeProductImages(int productID) throws SQLException {
        String query = "DELETE FROM " + getTablePrefix() + "_product_images WHERE product_id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setInt(1, productID);
            prepareStatement.execute();
        }
    }

}
