package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.CompilationNewDto;
import ru.practicum.dto.CompilationUpdateDto;
import ru.practicum.repository.EventRepo;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.repository.CompilationRepo;
import ru.practicum.util.UtilService;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CompilationService {

    private final CompilationRepo compilationRepo;
    private final EventRepo eventRepo;
    private final UtilService utilService;

    @Transactional
    public CompilationDto addCompilation(CompilationNewDto dto) {
        Compilation compilation = CompilationMapper.toCompilation(dto);

        if (compilation.getPinned() == null) {
            compilation.setPinned(false);
        }
        if (dto.getEvents() == null || dto.getEvents().isEmpty()) {
            compilation.setEvents(Collections.emptyList());
        } else {
            compilation.setEvents(eventRepo.findByIdIn(dto.getEvents()));
        }

        return CompilationMapper.toCompilationDto(compilationRepo.save(compilation));
    }

    @Transactional
    public CompilationDto updateCompilation(long compilationId, CompilationUpdateDto dto) {
        Compilation compilation = utilService.getCompilationIfExist(compilationId);

        if (compilation.getPinned() == null) {
            compilation.setPinned(false);
        }
        if (dto.getEvents() == null || dto.getEvents().isEmpty()) {
            compilation.setEvents(Collections.emptyList());
        } else {
            compilation.setEvents(eventRepo.findByIdIn(dto.getEvents()));
        }

        if (dto.getTitle() != null) {
            compilation.setTitle(dto.getTitle());
        }

        return CompilationMapper.toCompilationDto(compilationRepo.save(compilation));
    }

    @Transactional
    public void deleteCompilation(long compilationId) {
        utilService.getCompilationIfExist(compilationId);
        compilationRepo.deleteById(compilationId);
    }

    public CompilationDto getCompilationById(long compilationId) {
        Compilation compilation = utilService.getCompilationIfExist(compilationId);
        return CompilationMapper.toCompilationDto(compilation);
    }

    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        List<Compilation> compilations = compilationRepo.findByPinned(pinned, UtilService.toPage(from, size));
        return CompilationMapper.toCompilationDtoSet(compilations);
    }
}