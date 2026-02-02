package let_play.services;

import java.util.List;

import org.springframework.stereotype.Service;

import let_play.dtos.request.CreateProductRequest;
import let_play.dtos.request.UpdateProductRequest;
import let_play.dtos.response.ProductResponse;
import let_play.dtos.response.UserResponse;
import let_play.entities.Product;
import let_play.entities.Role;
import let_play.exceptions.ForbiddenException;
import let_play.exceptions.NotFoundException;
import let_play.repositories.ProductRepository;
import let_play.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> ProductResponse.fromEntity(product))
                .toList();
    }

    public ProductResponse getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product", "id", productId));
        return ProductResponse.fromEntity(product);
    }

    public List<ProductResponse> getProductsByUserId(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User", "id", userId);
        }

        return productRepository.findByUserId(userId).stream()
                .map(product -> ProductResponse.fromEntity(product))
                .toList();
    }

    public ProductResponse createProduct(CreateProductRequest request, String userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User", "id", userId);
        }

        Product newProduct = new Product();
        newProduct.setName(request.getName());
        newProduct.setDescription(request.getDescription());
        newProduct.setPrice(request.getPrice());
        newProduct.setUserId(userId);
        Product product = productRepository.save(newProduct);
        return ProductResponse.fromEntity(product);
    }

    public ProductResponse updateProduct(String id, UpdateProductRequest request, String userId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product", "id", id));

        if (!product.getUserId().equals(userId)) {
            throw new ForbiddenException("You don't have permission to update this product");
        }

        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        Product savedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(savedProduct);
    }

    public void deleteProduct(String productId, UserResponse user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product", "id", productId));

        // Allow deletion if user is the owner OR if user is an admin
        if (!product.getUserId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new ForbiddenException("You don't have permission to delete this product");
        }

        productRepository.deleteById(productId);
    }

}
