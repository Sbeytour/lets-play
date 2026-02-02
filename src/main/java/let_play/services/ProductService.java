package let_play.services;

import java.util.List;

import org.springframework.stereotype.Service;

import let_play.dtos.request.CreateProductRequest;
import let_play.dtos.request.UpdateProductRequest;
import let_play.dtos.response.ProductResponse;
import let_play.dtos.response.UserResponse;
import let_play.entities.Product;
import let_play.entities.Role;
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
                .map(porduct -> ProductResponse.fromEntity(porduct))
                .toList();
    }

    public ProductResponse getProductById(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> null);
        return ProductResponse.fromEntity(product);
    }

    public List<ProductResponse> getProductsByUserId(String userId) {
           if (!userRepository.existsById(userId)) {
            return null; //handle exception
        }

        return productRepository.findByUserId(userId).stream()
                .map(product -> ProductResponse.fromEntity(product))
                .toList();
    }

    public ProductResponse createProduct(CreateProductRequest request, String userId) {
        if (!userRepository.existsById(userId)) {
            return null; // handle exception
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
                .orElseThrow(() -> null); // handle exception

        if (!product.getUserId().equals(userId)) {
            return null; // forbiden exception
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
                .orElseThrow(() -> null);

        if (!product.getUserId().equals(user.getId()) && user.getRole() == Role.USER) {
            return; // forbiden exception
        }

        productRepository.deleteById(productId);
    }

}
