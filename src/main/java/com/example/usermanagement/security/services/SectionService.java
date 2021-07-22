package com.example.usermanagement.security.services;

import com.example.usermanagement.dto.SectionDTO;
import com.example.usermanagement.model.Section;
import com.example.usermanagement.repository.SectionRepository;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionService {
    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    UserRepository userRepository;

    public void saveSection(SectionDTO sectionDTO) {
        sectionRepository.save(this.toEntity(sectionDTO));
    }

    public void deleteSectionById(Long id) {
        Section section = sectionRepository.findById(id).orElse(null);
        List<Section> childList = sectionRepository.findAllByParentId(id).orElse(null);
        for (Section child : childList) {
            child.setParent(section.getParent());
        }
        sectionRepository.delete(section);
    }

    public Section toEntity(SectionDTO sectionDTO) {
        Section section = new Section();
        if (sectionDTO.getId() != null) {
            section = sectionRepository.findById(sectionDTO.getId()).orElse(null);
        }
        section.setName(sectionDTO.getName());
        section.setCode(sectionDTO.getCode());
        section.setDescription(sectionDTO.getDescription());
        section.setParent(sectionRepository.findById(sectionDTO.getParentId()).orElse(null));
        if (sectionDTO.getCreatorId() != null) {
            section.setCreator(userRepository.findById(sectionDTO.getCreatorId()).orElse(null));
        }
        if(sectionDTO.getUpdaterId() != null) {
            section.setUpdater(userRepository.findById(sectionDTO.getUpdaterId()).orElse(null));
        }
        section.setCreateDate(sectionDTO.getCreateDate());
        section.setUpdateDate(sectionDTO.getUpdateDate());
        return section;
    }

    public SectionDTO toDto(Section section) {
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setId(section.getId());
        sectionDTO.setName(section.getName());
        sectionDTO.setCode(section.getCode());
        sectionDTO.setDescription(section.getDescription());
        sectionDTO.setCreateDate(section.getCreateDate());
        sectionDTO.setUpdateDate(section.getUpdateDate());
        if (section.getParent() != null) {
            sectionDTO.setParentId(section.getParent().getId());
            sectionDTO.setParentName(section.getParent().getName());
        }
        if (section.getCreator() != null) {
            sectionDTO.setCreatorId(section.getCreator().getId());
            sectionDTO.setCreatorName(section.getCreator().getFullName());
        }
        if (section.getUpdater() != null) {
            sectionDTO.setUpdaterId(section.getUpdater().getId());
            sectionDTO.setUpdaterName(section.getUpdater().getFullName());
        }

        return sectionDTO;
    }

    public List<SectionDTO> getAllSection() {
        List<SectionDTO> listSection = sectionRepository.findAll().
                stream().map(this::toDto).collect(Collectors.toList());
        return listSection;
    }
}
