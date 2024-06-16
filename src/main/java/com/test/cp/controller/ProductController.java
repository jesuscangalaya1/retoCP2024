package com.test.cp.controller;

import com.test.cp.dto.request.ProductRequest;
import com.test.cp.dto.response.PageableResponse;
import com.test.cp.dto.response.ProductResponse;
import com.test.cp.dto.response.ServiceResponse;
import com.test.cp.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.File;
import java.util.List;

import static com.test.cp.util.AppConstants.*;

@Validated
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "PRODUCTO", description = "Operaciones permitidas sobre la entidad Producto")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Obtener la información de todos los productos paginados", description = "Obtener la información de todos los productos paginados")
    @GetMapping(value = "/pagination", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponse<PageableResponse<ProductResponse>> pageableProducts(
            @RequestParam(value = "pageNo", defaultValue = NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

        PageableResponse<ProductResponse> productPage = productService.paginationProducts(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        return new ResponseEntity<>(new ServiceResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK), productPage), HttpStatus.OK).getBody();

    }

    @Operation(summary = "Crear un nuevo producto", description = "Crear un nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<ProductResponse>> createProduct(@RequestBody @Valid ProductRequest productRequest) {

        ProductResponse productResponse = productService.createProduct(productRequest);
        return new ResponseEntity<>(new ServiceResponse<>(SUCCESS,
                String.valueOf(HttpStatus.CREATED), productResponse), HttpStatus.CREATED);

    }

    @Operation(summary = "Actualizar un producto existente por su ID", description = "Actualizar un producto existente por su ID")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<ProductResponse>> updateProduct(@Positive(message = ID_POSITIVE)
                                                                          @PathVariable Long id, @RequestBody @Valid ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProduct(id, productRequest);
        return new ResponseEntity<>(new ServiceResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK), productResponse), HttpStatus.OK);

    }

    @Operation(summary = "Obtener información de un producto por su ID", description = "Obtener información de un producto por su ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<ProductResponse>> getByIdProduct(@Positive(message = ID_POSITIVE)
                                                                           @PathVariable Long id) {
        ProductResponse productResponse = productService.getByIdProduct(id);
        return new ResponseEntity<>(new ServiceResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK), productResponse), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un producto por su ID",
            description = "Eliminacion logica de un producto por su ID, no se elimina fisicamente de la base de datos, solo se desactiva.")
    @DeleteMapping(value = "/{id}")
    public ServiceResponse<String> deleteProduct(@Positive(message = ID_POSITIVE)
                                                 @PathVariable Long id) {

        productService.deleteProduct(id);
        return new ServiceResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK), MESSAGE_ID_PRODUCT + id + SUCCESS_DELETED);
    }

    @Operation(summary = "Export en PDF o EXCEL",
            description = "Exporta la lista de productos en formato PDF o EXCEL, Ejemplo: digitar ´PDF´ o ´EXCEL´ como quieras menusculas o mayusculas.")
    @GetMapping(value = "/export-PDF-EXCEL", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> exportsFlights(
            @RequestParam(value = "pageNo", defaultValue = NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int pageSize,
            @RequestParam(value = "format", defaultValue = FORMATO_EXCEL_ABREVIATURA) @NotBlank String format,
            @RequestParam(value = "ordenarPor", defaultValue = ORDENAR_POR_DEFECTO) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = ORDENAR_DIRECCION_POR_DEFECTO) String sortDir)
            throws Exception {

        PageableResponse<ProductResponse> flightPage = productService.paginationProducts(pageNumber, pageSize, ordenarPor, sortDir);
        List<ProductResponse> flights = flightPage.getContent();
        File file = productService.exportProducts(flights, format);

        // Configurar las cabeceras de la respuesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());

        // Crear la respuesta HTTP con el objeto File
        FileSystemResource fileResource = new FileSystemResource(file);
        return new ResponseEntity<>(fileResource, headers, HttpStatus.OK);
    }

    @Operation(summary = "Búsqueda y Filtrado productos por nombre",
            description = "Busca productos cuyo nombre contenga la cadena proporcionada,  Ejemplo: busca ´tele´ y te buscara productos como televisión, teléfono, etc.")
    @GetMapping(value = "/filterByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<?>> findProductsByName(@RequestParam String name) {
        List<ProductResponse> productResponse = productService.findProductsByName(name);
        return new ResponseEntity<>(new ServiceResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK), productResponse), HttpStatus.OK);
    }
}





