//package com.upload.api.v1.files.mapper.impl;
//
//import com.home.api.v1.orders.dto.OrdersDto;
//import com.home.api.v1.orders.entity.Orders;
//import com.home.api.v1.orders.mapper.OrdersMapper;
//import com.home.api.v1.orders.resp.CheckoutResp;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Component
//public class OrdersMapperImpl implements OrdersMapper {
//    private final ModelMapper modelMapper;
//
//    @Override
//    public Orders toEntity(OrdersDto dto) {
//        return this.modelMapper.map(dto, Orders.class);
//    }
//
//    @Override
//    public OrdersDto toDto(Orders entity) {
//        return this.modelMapper.map(entity, OrdersDto.class);
//    }
//
//    @Override
//    public List<Orders> toEntity(List<OrdersDto> toEntityList) {
//        return this.modelMapper.map(toEntityList, new org.modelmapper.TypeToken<List<Orders>>() {
//        }.getType());
//    }
//
//    @Override
//    public List<OrdersDto> toDto(List<Orders> toDtoList) {
//        return this.modelMapper.map(toDtoList, new org.modelmapper.TypeToken<List<OrdersDto>>() {
//        }.getType());
//    }
//
//    @Override
//    public CheckoutResp mapperCheckoutResp(Orders orders) {
//        CheckoutResp resp = modelMapper.map(orders, CheckoutResp.class);
//        resp.setOrderId(orders.getId());
//        return resp;
//    }
//}
