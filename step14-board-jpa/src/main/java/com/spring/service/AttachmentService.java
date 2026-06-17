package com.spring.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.entity.Attachment;
import com.spring.entity.Post;
import com.spring.repository.AttachmentRepository;

/**
 * [첨부파일 처리 서비스 클래스]
 * 
 * @Service: 스프링의 서비스 레이어 컴포넌트로 등록하여 비즈니스 로직을 수행하도록 합니다.
 * @Transactional(readOnly = true): 
 *   클래스 전체에 기본적으로 트랜잭션 읽기 전용 모드를 설정합니다. 
 *   SELECT 쿼리만 수행하는 메서드들의 성능을 높이고 무분별한 변경 감지(Dirty Checking) 동작을 방지합니다.
 */
@Service
@Transactional(readOnly = true)
public class AttachmentService {
  private final AttachmentRepository attachmentRepository;
  private final Path uploadPath;

  /**
   * [생성자 주입 및 설정 정보(@Value) 수신]
   * 
   * @Value("${app.upload.dir}"): 
   *   application.properties 파일에 정의된 업로드 경로 설정값을 스프링이 찾아와 변수에 매핑해 줍니다.
   * Paths.get(uploadDir).toAbsolutePath(): 
   *   상대 경로 혹은 절대 경로 형태로 주어지는 디렉토리를 NIO Path 객체로 변환하여 보관합니다.
   */
  public AttachmentService(AttachmentRepository attachmentRepository,
    @Value("${app.upload.dir}") String uploadDir)
  {
    this.attachmentRepository = attachmentRepository;
    this.uploadPath = Paths.get(uploadDir).toAbsolutePath();
  }

  /**
   * [물리 디스크 파일 저장 및 데이터베이스 정보 등록]
   * 
   * @Transactional: 
   *   데이터 변경(INSERT, UPDATE, DELETE)이 수반되는 메서드이므로, readOnly = true 설정을 덮어쓰고 
   *   일반 트랜잭션 모드로 작동하여 작업 도중 예외(RuntimeException 등)가 나면 전체 작업을 롤백하도록 합니다.
   * 
   * @param files 클라이언트가 전송한 파일 배열 (MultipartFile)
   * @param post 첨부파일이 연결될 부모 게시글 엔티티
   * @throws IOException 입출력 오류 발생 시 컨트롤러로 예외를 전파합니다.
   */
  @Transactional
  public void saveFiles(MultipartFile[] files, Post post) throws IOException {
    // 1. 업로드된 파일 배열 자체가 없으면 로직을 종료합니다.
    if(files == null) return;
    
    // 2. 업로드한 파일들을 하나씩 꺼내어 서버 로컬 디렉토리에 물리적으로 복사(저장)합니다.
    for(MultipartFile file : files){
      // 파일이 비어있는 상태이거나 정상 업로드되지 않았다면 다음 파일로 넘어갑니다.
      if(file == null || file.isEmpty()) continue;  

      // 원본 파일 명을 가져옵니다. (예: "report.docx")
      String originalName = file.getOriginalFilename();
      String extension = "";
      
      // 파일명에서 마지막 '.' 위치를 찾아 확장자만 분리합니다. (예: ".docx")
      if(originalName != null && originalName.contains(".")){
        extension = originalName.substring(originalName.lastIndexOf("."));
      }
      
      // 중복되지 않는 36자리 문자열 UUID를 생성한 뒤 확장자를 붙여 고유 저장 파일명을 지정합니다.
      String storedName = UUID.randomUUID() + extension;
      
      // file.getInputStream()을 통해 임시 업로드된 파일 스트림을 열고, 
      // 지정한 실제 업로드 경로(uploadPath)에 고유 이름(storedName)으로 물리적 복사를 수행합니다.
      Files.copy(file.getInputStream(), uploadPath.resolve(storedName));
      
      // 3. DB에 물리 저장된 첨부파일 메타데이터를 저장(Insert)합니다.
      Attachment attachment = new Attachment();
      attachment.setOriginalName(originalName);
      attachment.setStoredName(storedName);
      attachment.setFileSize(file.getSize());
      attachment.setPost(post); // 어느 게시글에 연관된 첨부파일인지 부모 객체를 지정해 줍니다.
      
      attachmentRepository.save(attachment);
    }
  }

  public List<Attachment> getAttachmentByPost(Long id) {
    return attachmentRepository.findByPostId(id);
  }
}
