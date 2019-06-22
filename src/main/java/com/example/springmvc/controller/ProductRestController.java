package com.example.springmvc.controller;

import com.example.springmvc.model.Product;
import com.example.springmvc.other.SearchBean;
import com.example.springmvc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@RestController
public class ProductRestController {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping(path = "/searchResult", method = RequestMethod.GET)
    public List<Product> getAllEmployees() {
        return productRepository.findAll();
    }

    @RequestMapping(path = "/searchResult/{name}", method = RequestMethod.GET)
    public List<Product> getSearchProducts(@PathVariable(name = "name") String name) {
        return productRepository.findByNameIgnoreCaseContaining(name);
    }

    @RequestMapping(path = "/searchResult", method = RequestMethod.POST)
//    public List<Product> getProductsBySearchBean(@RequestBody SearchBean searchBean) {
    public List<Product> getProductsBySearchBean(@RequestParam Map<String, String> body) {
        SearchBean searchBean = new SearchBean();
        String id = body.get("id");
        if (id != null && !id.isEmpty()) {
            searchBean.setId(Long.valueOf(id));
        }
        String name = body.get("name");
        if (name != null && !name.isEmpty()) {
            searchBean.setName(name);
        }
        String description = body.get("description");
        if (description != null && !id.isEmpty()) {
            searchBean.setDescription(description);
        }
        String type = body.get("type");
        if (type != null && !type.isEmpty()) {
            searchBean.setType(type);
        }
        String category = body.get("category");
        if (category != null && !category.isEmpty()) {
            searchBean.setCategory(category);
        }
        String leftPriceField = body.get("leftPriceField");
        if (leftPriceField != null && !leftPriceField.isEmpty()) {
            searchBean.setLeftPriceField(Double.valueOf(leftPriceField));
        }
        String rightPriceField = body.get("rightPriceField");
        if (rightPriceField != null && !rightPriceField.isEmpty()) {
            searchBean.setRightPriceField(Double.valueOf(rightPriceField));
        }
        String leftOP = body.get("leftOP");
        if (leftOP != null && !leftOP.isEmpty()) {
            searchBean.setLeftOP(leftOP);
        }
        String rightOP = body.get("rightOP");
        if (rightOP != null && !rightOP.isEmpty()) {
            searchBean.setRightOP(rightOP);
        }
        if(searchBean.isEmpty()) {
            return productRepository.findAll();
        } else {
            Specification<Product> productSpecification = ProductSpecifications.search(searchBean);
            return productRepository.findAll(productSpecification);
        }
    }
}

final class ProductSpecifications {

    private ProductSpecifications() {
    }

    static Specification<Product> search(SearchBean searchBean) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> crit, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (searchBean.getId() != null) {
                    predicate = cb.equal(root.get("id"), searchBean.getId());
                }
                if (searchBean.getName() != null && !searchBean.getName().isEmpty()) {
                    if (predicate != null) {
                        predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + searchBean.getName() + "%"));
                    } else {
                        predicate = cb.like(cb.lower(root.get("name")), "%" + searchBean.getName() + "%");
                    }
                }
                if (searchBean.getDescription() != null && !searchBean.getDescription().isEmpty()) {
                    if (predicate != null) {
                        predicate = cb.and(predicate, cb.like(cb.lower(root.get("description")), "%" + searchBean.getDescription() + "%"));
                    } else {
                        predicate = cb.like(cb.lower(root.get("description")), "%" + searchBean.getDescription() + "%");
                    }
                }
                if (searchBean.getCategory() != null && !searchBean.getCategory().isEmpty()) {
                    if (predicate != null) {
                        predicate = cb.and(predicate, cb.like(cb.lower(root.get("category")), "%" + searchBean.getCategory() + "%"));
                    } else {
                        predicate = cb.like(cb.lower(root.get("category")), "%" + searchBean.getCategory() + "%");
                    }
                }
                if (searchBean.getType() != null && !searchBean.getType().isEmpty()) {
                    if (predicate != null) {
                        predicate = cb.and(predicate, cb.like(cb.lower(root.get("type")), "%" + searchBean.getType() + "%"));
                    } else {
                        predicate = cb.like(cb.lower(root.get("type")), "%" + searchBean.getType() + "%");
                    }
                }
                if (searchBean.getLeftPriceField() != null && searchBean.getLeftOP() != null && !searchBean.getLeftOP().isEmpty()) {
                    String leftOP = searchBean.getLeftOP();
                    if (leftOP.equals(">=")) {
                        if (predicate != null) {
                            predicate = cb.and(predicate, cb.ge(root.get("price"), searchBean.getLeftPriceField()));
                        } else {
                            predicate = cb.ge(root.get("price"), searchBean.getLeftPriceField());
                        }
                    } else if (leftOP.equals(">")) {
                        if (predicate != null) {
                            predicate = cb.and(predicate, cb.gt(root.get("price"), searchBean.getLeftPriceField()));
                        } else {
                            predicate = cb.gt(root.get("price"), searchBean.getLeftPriceField());
                        }
                    }
                }
                if (searchBean.getRightPriceField() != null && searchBean.getRightOP() != null && !searchBean.getRightOP().isEmpty()) {
                    String rightOP = searchBean.getRightOP();
                    if (rightOP.equals("<=")) {
                        if (predicate != null) {
                            predicate = cb.and(predicate, cb.le(root.get("price"), searchBean.getRightPriceField()));
                        } else {
                            predicate = cb.le(root.get("price"), searchBean.getRightPriceField());
                        }
                    } else if (rightOP.equals("<")) {
                        if (predicate != null) {
                            predicate = cb.and(predicate, cb.lt(root.get("price"), searchBean.getRightPriceField()));
                        } else {
                            predicate = cb.lt(root.get("price"), searchBean.getRightPriceField());
                        }
                    }
                }
                return predicate;
            }
        };
    }
}