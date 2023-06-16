package ru.drdrapp.drappogram.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.drdrapp.drappogram.models.DgMessage;
import ru.drdrapp.drappogram.repositories.DgMessageRepository;
import ru.drdrapp.drappogram.repositories.DgUserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DgMessageService {

    private final DgUserRepository dgUserRepository;
    private final DgMessageRepository dgMessageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public Page<DgMessage> getDgMessagesByFilter(String tagFilter,
                                                 Pageable pageable) {
        Page<DgMessage> dgMessages;
        if (tagFilter != null && !tagFilter.isEmpty()) {
            dgMessages = dgMessageRepository.findByTag(tagFilter, pageable);
        } else {
            dgMessages = dgMessageRepository.findAll(pageable);
        }
        return dgMessages;
    }

    public Page<DgMessage> getDgMessagesByAuthorId(long authorId,
                                                   String tagFilter,
                                                   Pageable pageable) {
        Page<DgMessage> dgMessages;
        if (tagFilter != null && !tagFilter.isEmpty()) {
            dgMessages = dgMessageRepository.findByAuthor_IdAndTag(authorId, tagFilter, pageable);
        } else {
            dgMessages = dgMessageRepository.findByAuthor_Id(authorId, pageable);
        }
        return dgMessages;
    }

    public void saveDgMessageFile(DgMessage dgMessage,
                                  MultipartFile messageFile) throws IOException {
        if (messageFile != null && !Objects.requireNonNull(messageFile.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                boolean wasFileSystemOperationResult = uploadDir.mkdir();
                if (!wasFileSystemOperationResult) {
                    System.out.println("Ошибка создания директории!");
                }
            }
            String resultFilename = UUID.randomUUID() + "." + messageFile.getOriginalFilename();
            messageFile.transferTo(new File(uploadPath + "/" + resultFilename));
            dgMessage.setFilename(resultFilename);
        }
    }

    public void saveDgMessage(DgMessage dgMessage) {
        dgMessageRepository.save(dgMessage);
    }

    public List<DgMessage> getListDgMessage() {
        return dgMessageRepository.findAll();
    }

    public void updateDgMessage(DgUserDetails dgUserDetails,
                                DgMessage dgMessage,
                                String text,
                                String tag,
                                MultipartFile messageFile) throws IOException {
        if (dgMessage.getAuthor().equals(dgUserDetails.getDgUser())) {
            if (StringUtils.hasLength(text)) {
                dgMessage.setText(text);
            }
            if (StringUtils.hasLength(tag)) {
                dgMessage.setTag(tag);
            }
            saveDgMessageFile(dgMessage, messageFile);
            saveDgMessage(dgMessage);
        }
    }
}