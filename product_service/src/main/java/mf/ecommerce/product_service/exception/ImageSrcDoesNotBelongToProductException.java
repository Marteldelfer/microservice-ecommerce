package mf.ecommerce.product_service.exception;

public class ImageSrcDoesNotBelongToProductException extends RuntimeException {
    public ImageSrcDoesNotBelongToProductException(String message) {
        super(message);
    }
}
