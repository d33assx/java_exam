package com.exam.dias.service;

import com.exam.dias.dto.AbdullayevDiasCategoryDto;
import com.exam.dias.entity.AbdullayevDiasCategory;
import com.exam.dias.exception.AbdullayevDiasResourceNotFoundException;
import com.exam.dias.repository.AbdullayevDiasCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbdullayevDiasCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(AbdullayevDiasCategoryService.class);

    private final AbdullayevDiasCategoryRepository categoryRepository;

    public AbdullayevDiasCategoryService(AbdullayevDiasCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<AbdullayevDiasCategoryDto> getAllCategories() {
        logger.info("Fetching all categories");
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AbdullayevDiasCategoryDto getCategoryById(Long id) {
        logger.info("Fetching category by id: {}", id);
        AbdullayevDiasCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Category not found"));
        return convertToDto(category);
    }

    public AbdullayevDiasCategoryDto createCategory(AbdullayevDiasCategoryDto dto) {
        logger.info("Creating new category: {}", dto.getName());
        AbdullayevDiasCategory category = new AbdullayevDiasCategory();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        AbdullayevDiasCategory saved = categoryRepository.save(category);
        return convertToDto(saved);
    }

    public AbdullayevDiasCategoryDto updateCategory(Long id, AbdullayevDiasCategoryDto dto) {
        logger.info("Updating category: {}", id);
        AbdullayevDiasCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Category not found"));
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        AbdullayevDiasCategory updated = categoryRepository.save(category);
        return convertToDto(updated);
    }

    public void deleteCategory(Long id) {
        logger.info("Deleting category: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new AbdullayevDiasResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    private AbdullayevDiasCategoryDto convertToDto(AbdullayevDiasCategory category) {
        AbdullayevDiasCategoryDto dto = new AbdullayevDiasCategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
