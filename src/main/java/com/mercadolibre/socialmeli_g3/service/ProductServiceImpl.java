package com.mercadolibre.socialmeli_g3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.socialmeli_g3.dto.ProductDTO;
import com.mercadolibre.socialmeli_g3.entity.Product;
import com.mercadolibre.socialmeli_g3.exception.BadRequestException;
import com.mercadolibre.socialmeli_g3.repository.IPostRepository;
import com.mercadolibre.socialmeli_g3.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
}
