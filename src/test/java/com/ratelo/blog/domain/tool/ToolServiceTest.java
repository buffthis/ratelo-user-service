package com.ratelo.blog.domain.tool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ToolServiceTest {
    @Mock
    private ToolRepository toolRepository;
    @InjectMocks
    private ToolService toolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Tool이 존재하면 해당 Tool을 반환한다")
    void shouldReturnTool_whenToolExists() {
        // given
        final Tool tool = ToolFixture.createDefaultTool();
        when(toolRepository.findById(tool.getId())).thenReturn(Optional.of(tool));

        // when
        final Tool result = toolService.getToolById(tool.getId());

        // then
        assertNotNull(result);
        assertEquals(tool.getId(), result.getId());
    }

    @Test
    @DisplayName("Tool이 존재하지 않으면 null을 반환한다")
    void shouldReturnNull_whenToolDoesNotExist() {
        // given
        final Long notExistToolId = 1L;
        when(toolRepository.findById(notExistToolId)).thenReturn(Optional.empty());

        // when
        final Tool result = toolService.getToolById(notExistToolId);

        // then
        assertNull(result);
    }
}