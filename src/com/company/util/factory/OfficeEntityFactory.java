package com.company.util.factory;

import com.company.domain.OfficeEntity;
import com.company.exceptions.DuplicateOfficeException;
import com.company.service.entity.OfficeService;

public class OfficeEntityFactory {

    private OfficeEntityFactory(){
    }

    public static OfficeEntity getOffice(String name, String address) {
        if (OfficeService.getInstance().getOffice(name) != null)
            throw new DuplicateOfficeException("Exista deja acest office");

        OfficeEntity officeEntity = new OfficeEntity();
        officeEntity.setName(name);
        officeEntity.setAddress(address);
        return officeEntity;
    }
}
