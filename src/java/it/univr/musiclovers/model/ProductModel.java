package it.univr.musiclovers.model;

import it.univr.musiclovers.model.beans.ProductBean;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Marian Solomon
 */
public abstract class ProductModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public List<ProductBean> getProducts() throws SQLException {
        ArrayList<ProductBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + getTablePrefix() + "_PRODUCT";
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    result.add(makeProduct(resultSet));
                }
            }
        }
        return result;
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

    public static ProductBean getProduct(int product_id) throws SQLException {
        ProductBean result = new ProductBean();
        String query = "SELECT * FROM " + getTablePrefix() + "_PRODUCT WHERE id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setInt(1, product_id);
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = (makeProduct(resultSet));
                }
            }
        }
        return result;
    }

    public static List<String> getProductImages(int product_id) throws SQLException {
        LinkedList<String> result = new LinkedList<>();
        String query = "SELECT image FROM " + getTablePrefix() + "_PRODUCT_IMAGES WHERE product_id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setInt(1, product_id);
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
        String query = "SELECT * FROM " + getTablePrefix() + "_PRODUCT ";
        if (filters.size() > 0) {
            query += "WHERE ";
            for (Iterator<Entry<String, Boolean>> it = filters.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Boolean> entry = it.next();
                query += entry.getKey() + " = ? ";
                if (it.hasNext()) {
                    query += "AND ";
                }
            }
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

    public static ProductBean makeProduct(ResultSet resultSet) throws SQLException {
        ProductBean productBean = new ProductBean();
        productBean.setId(resultSet.getInt("id"));
        productBean.setEnable(resultSet.getBoolean("status"));
        productBean.setOnline(resultSet.getBoolean("online"));
        productBean.setInexpensive(resultSet.getBoolean("inexpensive"));
        productBean.setWeight(resultSet.getFloat("weight"));
        productBean.setPrice(resultSet.getFloat("price"));
        productBean.setName(resultSet.getString("name"));
        productBean.setDescription(resultSet.getString("description"));
        productBean.setFor_child(resultSet.getBoolean("for_child"));
        productBean.setMinAge(resultSet.getInt("min_age"));
        productBean.setProfessional(resultSet.getBoolean("professional"));
        productBean.setUsed(resultSet.getBoolean("used"));
        productBean.setBrand(BrandModel.getBrand(resultSet.getInt("brand_id")));
        productBean.setProductImage(getProductImages(productBean.getId()));
        return productBean;
    }

}