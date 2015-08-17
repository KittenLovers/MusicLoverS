package it.univr.musiclovers.model;

import java.util.Map;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import it.univr.musiclovers.model.beans.ProductBean;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author Marian Solomon
 */
public class ProductModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private final BrandModel brandModel;

    public ProductModel() {
        this.brandModel = new BrandModel();
    }

    public ProductBean getProduct(int product_id) {
        ProductBean result = new ProductBean();
        try {
            String query = "SELECT * FROM " + ConnectionModel.getInstance().getTablePrefix() + "_PRODUCT WHERE id = ?";
            Connection connection = ConnectionModel.getInstance().getConnection();
            try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
                prepareStatement.setInt(1, product_id);
                ResultSet resultSet = prepareStatement.executeQuery();
                if (resultSet.next()) {
                    result = (makeProduct(resultSet));
                }
            }
        } catch (SQLException ex) {
            for (Throwable throwable : ex) {
                System.err.println(throwable);
            }
        }
        return result;
    }

    public List<ProductBean> getProducts() {
        ArrayList<ProductBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + ConnectionModel.getInstance().getTablePrefix() + "_PRODUCT";
        Connection connection = ConnectionModel.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(makeProduct(resultSet));
            }
        } catch (SQLException ex) {
            for (Throwable throwable : ex) {
                System.err.println(throwable);
            }
        }
        return result;
    }

    public List<ProductBean> getProducts(Map<String, Boolean> filters) {
        ArrayList<ProductBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + ConnectionModel.getInstance().getTablePrefix() + "_PRODUCT ";
        if (filters.size() > 0) {
            query += "WHERE ";
            for (Iterator<Entry<String, Boolean>> it = filters.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Boolean> entry = it.next();
                query += entry.getKey() + " = ? ";
                if (it.hasNext()) {
                    query += "AND ";
                }
            }
            /*query += "for_child = ? ";
             query += "AND inexpensive = ? ";
             query += "AND professional = ? ";
             query += "AND used = ? ";*/
        }
        Connection connection = ConnectionModel.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            int i = 0;
            for (Entry<String, Boolean> entry : filters.entrySet()) {
                preparedStatement.setBoolean(++i, entry.getValue());
            }
            /*preparedStatement.setBoolean(1, filters.get("for_child"));
             preparedStatement.setBoolean(2, filters.get("inexpensive"));
             preparedStatement.setBoolean(3, filters.get("professional"));
             preparedStatement.setBoolean(4, filters.get("used"));*/
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(makeProduct(resultSet));
            }
        } catch (SQLException ex) {
            for (Throwable throwable : ex) {
                System.err.println(throwable);
            }
        }
        return result;
    }

    public List<ProductBean> getOnlineProducts() {
        ArrayList<ProductBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + ConnectionModel.getInstance().getTablePrefix() + "_PRODUCT WHERE online = 'true'";
        Connection connection = ConnectionModel.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(makeProduct(resultSet));
            }
        } catch (SQLException ex) {
            for (Throwable throwable : ex) {
                System.err.println(throwable);
            }
        }
        return result;
    }

    public List<String> getProductImages(int product_id) {
        LinkedList<String> result = new LinkedList<>();
        String query = "SELECT image FROM " + ConnectionModel.getInstance().getTablePrefix() + "_PRODUCT_IMAGES WHERE product_id = ?";
        Connection connection = ConnectionModel.getInstance().getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setInt(1, product_id);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("image"));
            }
        } catch (SQLException ex) {
            for (Throwable throwable : ex) {
                System.err.println(throwable);
            }
        }
        return result;
    }

    public ProductBean makeProduct(ResultSet resultSet) {
        ProductBean productBean = new ProductBean();
        try {
            productBean.setId(resultSet.getInt("id"));
            productBean.setEnable(resultSet.getBoolean("status"));
            productBean.setOnline(resultSet.getBoolean("online"));
            productBean.setInexpensive(resultSet.getBoolean("inexpensive"));
            productBean.setWeight(resultSet.getFloat("weight"));
            productBean.setPrice(resultSet.getFloat("price"));
            productBean.setName(resultSet.getString("name"));
            productBean.setDescription(resultSet.getString("description"));
            productBean.setFor_child(resultSet.getBoolean("for_child"));
            productBean.setMin_age(resultSet.getInt("min_age"));
            productBean.setProfessional(resultSet.getBoolean("professional"));
            productBean.setUsed(resultSet.getBoolean("used"));
            productBean.setBrand(brandModel.getBrand(resultSet.getInt("brand_id")));
            productBean.setProduct_image(getProductImages(productBean.getId()));
        } catch (SQLException ex) {
            for (Throwable throwable : ex) {
                System.err.println(throwable);
            }
        }
        return productBean;
    }

}
