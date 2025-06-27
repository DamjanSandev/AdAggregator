package adg.backend.service.domain.impl;

import adg.backend.model.domain.Brand;
import adg.backend.model.domain.Model;
import adg.backend.repository.ModelRepository;
import adg.backend.service.domain.ModelService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<Model> findByBrand(Brand brand) {
        return modelRepository.findByBrand(brand);
    }
}
