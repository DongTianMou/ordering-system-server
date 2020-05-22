package com.dongtian.orderingsystem.service.impl;

import com.dongtian.orderingsystem.dao.ProductInfoRepository;
import com.dongtian.orderingsystem.dto.CartDto;
import com.dongtian.orderingsystem.enums.ProductStatusEnum;
import com.dongtian.orderingsystem.enums.ResultEnum;
import com.dongtian.orderingsystem.exceptions.ParamException;
import com.dongtian.orderingsystem.pojo.ProductInfo;
import com.dongtian.orderingsystem.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.getOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus( ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll( pageable );
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save( productInfo );
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        //遍历list，拿到购物车中商品id
        for (CartDto cartDto: cartDtoList) {
            String productId = cartDto.getProductId();
            ProductInfo productInfo = productInfoRepository.getOne(productId);
            if (productInfo == null) {
                throw new ParamException( ResultEnum.PRODUCT_NOT_EXIST);
            }
            //拿到商品查库存并 + 购物车中的数量
            Integer stock = productInfo.getProductStock() + cartDto.getProductQuantity();
            //设置新库存
            productInfo.setProductStock(stock);

            productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        //遍历list，拿到购物车中商品id
        for (CartDto cartDto: cartDtoList) {
            String productId = cartDto.getProductId();
            ProductInfo productInfo = productInfoRepository.getOne(productId);
            if (productInfo == null) {
                throw new ParamException( ResultEnum.PRODUCT_NOT_EXIST);
            }
            //拿到商品查库存并 - 购物车中的数量
            Integer stock = productInfo.getProductStock() - cartDto.getProductQuantity();
            if (stock < 0) {
                log.info("库存不足");
                throw new ParamException(ResultEnum.PRODUCT_STOCK_NOTENOUGH);
            }
            Integer sellCount = productInfo.getSellCount() + cartDto.getProductQuantity();

            //设置新库存
            productInfo.setProductStock(stock);

            //设置销售量
            productInfo.setSellCount(sellCount);

            productInfoRepository.save(productInfo);
        }
    }
    /*商品上架，判断商品状态，修改为状态*/
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoRepository.getOne(productId);
        if (productInfo == null) {
            throw new ParamException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new ParamException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoRepository.getOne(productId);
        if (productInfo == null) {
            throw new ParamException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new ParamException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoRepository.save(productInfo);
    }
}
