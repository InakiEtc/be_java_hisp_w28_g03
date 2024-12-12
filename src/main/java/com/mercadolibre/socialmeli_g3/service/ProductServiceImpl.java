package com.mercadolibre.socialmeli_g3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IUserService iUserService;

    public ProductServiceImpl(IUserService iUserService) {
        this.iUserService = iUserService;
    }

}
