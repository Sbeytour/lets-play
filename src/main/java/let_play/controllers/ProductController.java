package let_play.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import let_play.dtos.request.CreateProductRequest;
import let_play.dtos.request.UpdateProductRequest;
import let_play.dtos.response.ProductResponse;
import let_play.dtos.response.UserResponse;
import let_play.services.ProductService;
import let_play.services.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        UserResponse user = userService.getCurrentUser();

        ProductResponse savedProduct = productService.createProduct(request, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ProductResponse>> getMyProducts() {
        UserResponse user = userService.getCurrentUser();
        List<ProductResponse> products = productService.getProductsByUserId(user.getId());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductResponse>> getProductsByUser(@PathVariable String userId) {
        List<ProductResponse> products = productService.getProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        UserResponse user = userService.getCurrentUser();

        productService.deleteProduct(productId, user);

        String response = "Product with id " + productId + " has been deleted";
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String productId,
            @Valid @RequestBody UpdateProductRequest request) {
        UserResponse user = userService.getCurrentUser();

        ProductResponse updatedProduct = productService.updateProduct(productId, request, user.getId());
        return ResponseEntity.ok(updatedProduct);
    }
}
