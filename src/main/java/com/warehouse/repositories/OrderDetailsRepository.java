package com.warehouse.repositories;

import com.warehouse.repositories.entities.ItemDetailsEntity;
import com.warehouse.repositories.entities.OrderDetailsEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface OrderDetailsRepository {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Select("SELECT MAKE_ORDER_ID() FROM DUAL")
    int getNextOrderId();      //sql method linking to java, so we can call that in service

    @Insert("insert into ORDER_DETAILS(ORDER_ID,ORDER_PLACED_BY,WAREHOUSE_PINCODE) values(#{entity.orderId},#{entity.orderPlacedBy},#{entity.warehousePincode})")
    @Transactional()
    void insertOrderDetails(@Param("entity") OrderDetailsEntity orderDetailsEntity);  //add order to order_details table

    @Insert({
            "<script>",
            "INSERT INTO ITEM_DETAILS (ITEM_ID, ITEM_QUANTITY, ORDER_ID) VALUES ",
            "<foreach collection='entities' item='entity' separator=','>",
            "(#{entity.itemId}, #{entity.itemQuantity}, #{entity.orderId})",
            "</foreach>",
            "</script>"
    })
    @Transactional()
    void insertItemDetails(@Param("entities") List<ItemDetailsEntity> itemList);

    @Select("SELECT od.ORDER_ID, od.ORDER_PLACED_BY, od.WAREHOUSE_PINCODE " +
            "FROM warehouse.order_details od " +
            "WHERE od.ORDER_ID = #{orderId}")
    @Results({
            @Result(property = "orderId", column = "ORDER_ID"),
            @Result(property = "orderPlacedBy", column = "ORDER_PLACED_BY"),
            @Result(property = "warehousePincode", column = "WAREHOUSE_PINCODE"),
            @Result(property = "itemDetails", column = "ORDER_ID",
                    many = @Many(select = "getItemsByOrderId"))
    })
    OrderDetailsEntity getOrderDetailsByOrderId(long orderId);

    @Select(value = "SELECT " +
            "    id.ITEM_ID, " + "    id.ITEM_QUANTITY " +
            "FROM warehouse.item_details id " +
            "WHERE id.ORDER_ID = #{orderId}")
    @Results({
            @Result(property = "itemId", column = "ITEM_ID"),
            @Result(property = "itemQuantity", column = "ITEM_QUANTITY")
    })
    List<ItemDetailsEntity> getItemsByOrderId(long orderId);

}
