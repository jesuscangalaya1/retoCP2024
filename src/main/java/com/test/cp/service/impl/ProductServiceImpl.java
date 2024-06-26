package com.test.cp.service.impl;

import com.test.cp.dto.request.ProductRequest;
import com.test.cp.dto.response.PageableResponse;
import com.test.cp.dto.response.ProductResponse;
import com.test.cp.entity.ProductEntity;
import com.test.cp.exceptions.BusinessException;
import com.test.cp.mapper.ProductMapper;
import com.test.cp.report.exports.ResourceExport;
import com.test.cp.repository.ProductRepository;
import com.test.cp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.test.cp.util.AppConstants.*;
import static com.test.cp.util.ExportDataUtils.*;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ResourceExport resourceExport;

    @Transactional(readOnly = true)
    @Override
    public PageableResponse<ProductResponse> paginationProducts(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina - 1, medidaDePagina, sort);

        // Obtener una página de productos desde el repositorio
        Page<ProductEntity> products = productRepository.findAllByDeletedFalse(pageable);

        // Mapear la página de entidades a una página de DTOs
        List<ProductResponse> productResponsePage = productMapper.toListProductsDTO(products.getContent());

        if (productResponsePage.isEmpty()) {
            throw new BusinessException("P-204", HttpStatus.NO_CONTENT, "Lista Vaciá de Productos");
        }

        return PageableResponse.<ProductResponse>builder()
                .content(productResponsePage)
                .pageNumber(products.getNumber() + 1)
                .pageSize(products.getSize())
                .totalPages(products.getTotalPages())
                .totalElements(products.getTotalElements())
                .last(products.isLast())
                .build();
    }

    @Transactional
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        // Mapear la solicitud del producto a una entidad de producto
        ProductEntity product = productMapper.toProductEntity(productRequest);

        // Guardar la entidad del producto en la base de datos
        ProductEntity savedProduct = productRepository.save(product);
        // Mapear la entidad del producto guardado a un DTO de respuesta
        return productMapper.toProductDTO(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        // Buscar el producto existente por su ID
        ProductEntity product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_PRODUCT + id));
        // Actualizar los atributos del producto existente desde el DTO de solicitud
        productMapper.updateProductFromDto(productRequest, product);
        // Guardar el producto actualizado en la base de datos
        product = productRepository.save(product);
        // Mapear la entidad del producto actualizado a un DTO de respuesta
        return productMapper.toProductDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getByIdProduct(Long id) {
        ProductEntity product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_PRODUCT + id));

        return productMapper.toProductDTO(product);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findByIdAndDeletedFalse(id);
        if (productEntity.isEmpty()) {
            throw new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_PRODUCT + id);
        }
        // Desactivar (eliminar lógicamente) un producto por su ID
        productRepository.desactivarProduct(id);
    }

    @Transactional
    @Override
    public File exportProducts(List<ProductResponse> productResponses, String formato) throws Exception {
        validateFormato(formato);

        List<String> sheets = Collections.singletonList(SHEET_PRODUCT);

        Map<String, List<String>> colsBySheet = createColumnsBySheetMap();
        Map<String, List<Map<String, String>>> valuesBySheet = createValuesBySheetMap(productResponses);

        String reportName = REPORT_NAME_PRODUCT_PAGINABLE;
        if (formato.equalsIgnoreCase(FORMATO_EXCEL_ABREVIATURA)) {
            return resourceExport.generateExcel(sheets, colsBySheet, valuesBySheet, reportName);
        } else {
            return resourceExport.generatePdf(sheets, colsBySheet, valuesBySheet, reportName);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findProductsByName(String name) {
        List<ProductEntity> products = productRepository.findByNameContainingIgnoreCaseAndDeletedFalse(name);
        if (products.isEmpty()) {
            throw new BusinessException("P-404", HttpStatus.NOT_FOUND, BAD_REQUEST_PRODUCT_NAME + name);
        }

        return productMapper.toListProductsDTO(products);
    }


}
