package com.example.catalogservice.controller;

import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("catalog-service")
public class CatalogController {

  private final CatalogService catalogService;
  private final ModelMapper modelMapper;

  public CatalogController(CatalogService catalogService, ModelMapper modelMapper) {
    this.catalogService = catalogService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/health_check")
  public String status(HttpServletRequest request) {
    return String.format("It's Working in User Service on PORT %s", request.getServerPort());
  }

  @GetMapping("/catalogs")
  public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
    Iterable<CatalogEntity> allCatalogs = catalogService.getAllCatalogs();
    List<ResponseCatalog> result = new ArrayList<>();
    for (CatalogEntity catalog : allCatalogs) {
      result.add(modelMapper.map(catalog, ResponseCatalog.class));
    }
    return ResponseEntity.ok(result);
  }

}
