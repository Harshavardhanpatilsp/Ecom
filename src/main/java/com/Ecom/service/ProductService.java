package com.Ecom.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Ecom.Model.Product;
import com.Ecom.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo repo;
	
	public List<Product> getallproducts(){
		return repo.findAll();	
	}

	public Product getprouct(int ProdID) {
		return repo.findById(ProdID).orElse(null);
	}

//	public Product addproduct(Product product, MultipartFile imagefile) throws IOException {
//		// TODO Auto-generated method stub
//		product.setImagename(imagefile.getName());
//		product.setImagetype(imagefile.getContentType());
//		product.setImagedata(imagefile.getBytes());
//		return repo.save(product);
//	}
	
	public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {

        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }


//    public List<Product> searchProducts(String keyword) {
//        return repo.searchProducts(keyword);
//    }
}
