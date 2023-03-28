package org.acme.service;

import org.acme.dto.ProductDTO;
import org.acme.entity.ProductEntity;
import org.acme.repository.ProductRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    private ProductRepository productRepository;

    public List<ProductDTO> findAllProducts(){
        List<ProductDTO> products = new ArrayList<>();

        productRepository.findAll().stream().forEach(item ->{
            products.add(mapProductEntityToDTO(item));
        });

        return products;
    }

    public void createNewProduct(ProductDTO productDTO){
        productRepository.persist(mapProductDtoToEntity(productDTO));
    }

    public void changeProduct(Long id, ProductDTO product){
        ProductEntity productEntity = productRepository.findById(id);

        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setCategory(product.getCategory());
        productEntity.setModel(product.getModel());
        productEntity.setPrice(product.getPrice());

        productRepository.persist(productEntity);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    private ProductDTO mapProductEntityToDTO(ProductEntity product){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(product.getCategory());
        productDTO.setModel(product.getModel());
        productDTO.setPrice(product.getPrice());

        return productDTO;
    }

    private ProductEntity mapProductDtoToEntity(ProductDTO productDTO){
        ProductEntity productEntity = new ProductEntity();

        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setCategory(productDTO.getCategory());
        productEntity.setModel(productDTO.getModel());
        productEntity.setPrice(productDTO.getPrice());

        return productEntity;
    }
}
