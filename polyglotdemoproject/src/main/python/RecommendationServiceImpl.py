import java
# import numpy as np
from typing import List

Product = java.type("com.polyglot.demo.project.entity.Product")


class RecommendationServiceImpl:
    @staticmethod
    def recommend(product: Product, products: List[Product]) -> Product:
      
        # product_vector = np.array(product.vector)
        # similarities = []
        
        # for other_product in products:
        #     other_vector = np.array(other_product.vector)
        #     cosine_similarity = np.dot(product_vector, other_vector) / (np.linalg.norm(product_vector) * np.linalg.norm(other_vector))
        #     similarities.append((other_product, cosine_similarity))
        
        # sorted_products = sorted(similarities, key=lambda x: x[1], reverse=True)
        
        # # Return the most similar product, excluding the product itself
        # return sorted_products[0][0] if sorted_products else None
    
        return products[0]