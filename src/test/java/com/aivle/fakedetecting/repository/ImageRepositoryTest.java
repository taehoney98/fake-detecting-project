package com.aivle.fakedetecting.repository;

import com.aivle.fakedetecting.entity.Image;
import com.aivle.fakedetecting.entity.Board;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void save_ShouldPersistImage_WhenValidImageProvided() {
        // Arrange
        Board board = boardRepository.save(new Board());
        Image image = new Image();
        image.setName("sample_image.png");
        image.setBoard(board);

        // Act
        Image savedImage = imageRepository.save(image);

        // Assert
        Optional<Image> foundImage = imageRepository.findById(savedImage.getId());
        assertThat(foundImage).isPresent();
        assertThat(foundImage.get().getName()).isEqualTo("sample_image.png");
        assertThat(foundImage.get().getBoard().getId()).isEqualTo(1L);
    }

    @Test
    public void save_ShouldUpdateExistingImage_WhenImageWithSameIdProvided() {
        // Arrange
        Board board = boardRepository.save(new Board());
        Image image = new Image();
        image.setName("initial_image.png");
        image.setBoard(board);
        Image savedImage = imageRepository.save(image);

        // Act
        savedImage.setName("updated_image.png");
        Image updatedImage = imageRepository.save(savedImage);

        // Assert
        Optional<Image> foundImage = imageRepository.findById(updatedImage.getId());
        assertThat(foundImage).isPresent();
        assertThat(foundImage.get().getName()).isEqualTo("updated_image.png");
    }

    @Test
    public void save_ShouldAssignIdToImage_WhenSaved() {
        // Arrange
        Board board = boardRepository.save(new Board());
        Image image = new Image();
        image.setName("new_image.png");
        image.setBoard(board);

        // Act
        Image savedImage = imageRepository.save(image);

        // Assert
        assertThat(savedImage.getId()).isNotNull();
    }
}