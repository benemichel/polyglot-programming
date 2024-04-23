#import site
import java
import numpy as np
from typing import List

Product = java.type("com.polyglot.demo.project.entity.Product")
ProductTags = java.type("com.polyglot.demo.project.enums.ProductTags")


class RecommendationServiceImpl:
    @staticmethod
    def recommend(product: Product, products: List[Product]) -> Product:

        product_vector = {
            "product": product,
            "vector": to_vector(product.getTags())
        }
        
        product_vectors = [
            {"product": product, 
            "vector": to_vector(product.getTags())}
            for product in products
        ]
        
        for other_product_vector in product_vectors:
            other_product_vector["similarity"] = cosine_similarity(product_vector["vector"], other_product_vector["vector"])
       
        product_vectors.sort(key=lambda item: item.get("similarity"), reverse=True)

        return product_vectors[1]["product"]
    

def cosine_similarity(vector1, vector2) -> float:
    dot_product = np.dot(vector1, vector2)
    norm1 = np.linalg.norm(vector1)
    norm2 = np.linalg.norm(vector2)

    return dot_product / (norm1 * norm2)

def to_vector(tags):
    all_tags = list(ProductTags.values())
    return np.array(list(map(lambda tag: int(tag in tags), all_tags)))