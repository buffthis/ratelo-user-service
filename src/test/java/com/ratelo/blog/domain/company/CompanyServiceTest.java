package com.ratelo.blog.domain.company;

import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.image.ImageRepository;
import com.ratelo.blog.dto.company.CompanyPatchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private CompanyPatchMapper companyPatchMapper;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doAnswer(invocation -> {
            CompanyPatchRequest req = invocation.getArgument(0);
            Company comp = invocation.getArgument(1);
            if (req.getName() != null) comp.setName(req.getName());
            return null;
        }).when(companyPatchMapper).updateCompanyFromDto(any(), any());
    }

    @Test
    void patchCompany_updatesNameOnly() {
        // given
        Company company = new Company();
        company.setId(1L);
        company.setName("Old Name");
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(companyRepository.save(any(Company.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CompanyPatchRequest request = new CompanyPatchRequest();
        request.setName("New Name");

        // when
        Company result = companyService.patchCompany(1L, request);

        // then
        assertEquals("New Name", result.getName());
        verify(companyPatchMapper).updateCompanyFromDto(request, company);
    }
}