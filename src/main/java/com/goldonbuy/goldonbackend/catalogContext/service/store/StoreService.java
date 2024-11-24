package com.goldonbuy.goldonbackend.catalogContext.service.store;

import com.goldonbuy.goldonbackend.catalogContext.entity.Store;
import com.goldonbuy.goldonbackend.catalogContext.exceptions.RessourceNotFoundException;
import com.goldonbuy.goldonbackend.catalogContext.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService implements IStoreService {

    private final StoreRepository storeRepository;

    @Override
    public Store getStoreById(Long id) {
        return this.storeRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("This store not found !"));
    }
}
