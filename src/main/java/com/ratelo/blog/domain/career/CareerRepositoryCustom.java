package com.ratelo.blog.domain.career;

import java.util.List;

public interface CareerRepositoryCustom {
    List<Career> findAllWithHiddenOption(Boolean includeHidden);
} 