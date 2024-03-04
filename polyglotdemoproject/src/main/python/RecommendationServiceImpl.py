import java
# import numpy as np
from typing import List

Product = java.type("com.polyglot.demo.project.entity.Product")


class RecommendationServiceImpl:
    @staticmethod
    def recommend(product: Product, products: List[Product]) -> Product:
        # project_vector = np.array(list(map(lambda skill: 1, project.skills())))
        
        # employee_vectors = {
        #     employee: to_vector(employee.getSkills(), project.skills())
        #     for employee in employees
        # }
        
        # distances = {
        #     employee: np.linalg.norm(project_vector - skill_vector)
        #     for employee, skill_vector in employee_vectors.items()
        # }
        
        # ordered = dict(sorted(distances.items(), key=lambda distance: distance[1]))
        recommendedProduct = products[2];

        return recommendedProduct

        
# def to_vector(employee_skills, searched_skills):
#     return np.array(list(map(lambda skill: int(skill in employee_skills), searched_skills)))