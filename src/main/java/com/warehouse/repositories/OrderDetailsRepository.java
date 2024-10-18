package com.warehouse.repositories;

import com.warehouse.repositories.entities.ItemDetailsEntity;
import com.warehouse.repositories.entities.OrderDetailsEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
            "INSERT INTO ITEM_DETAILS (ITEM_NAME, ITEM_QUANTITY, ORDER_ID) VALUES ",
            "<foreach collection='entities' item='entity' separator=','>",
            "(#{entity.itemName}, #{entity.itemQuantity}, #{entity.orderId})",
            "</foreach>",
            "</script>"
    })
    @Transactional()
    void insertItemDetails(@Param("entities") List<ItemDetailsEntity> itemList);

}
