package com.rojek.projectpp.Pages.Cards.Services;

import com.rojek.projectpp.Pages.Cards.Models.Cards;
import com.rojek.projectpp.Pages.Cards.Repositories.CardsRepository;
import com.rojek.projectpp.Security.Model.Users;
import com.rojek.projectpp.Security.Services.UsersService2;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@PropertySource("config.properties")
public class CardsService {

    private final CardsRepository cardsRepository;
    private final UsersService2 usersService;

    @Value("${files.location}")
    private String uploadPath;
    @Value("${image.size_medium}")
    private Integer imageSize;

    private Logger logger = LoggerFactory.getLogger(CardsService.class);

    public CardsService(CardsRepository cardsRepository, UsersService2 usersService) {
        this.cardsRepository = cardsRepository;
        this.usersService = usersService;
    }

    public List<Cards> getAllCards(String username) {
        Users u = usersService.getUserByLogin(username);
        return cardsRepository.findByUser(u);
    }

    public Cards createNewCard(String username) {
        Users u = usersService.getUserByLogin(username);
        return new Cards(u);
    }

    public void saveNewCard(Cards cards) {
        Random r = new Random();
        int [] numbers = new int[16];
        String result = "";

        do {
            for (int i = 0; i<numbers.length; i++)
                numbers[i] = r.nextInt(9 - 0 + 1) + 0;

            for (int i = 0; i< numbers.length; i++)
                result = new StringBuilder(result).append(numbers[i]).toString();
        } while (cardsRepository.findByNumber(result).isPresent());

        cards.setCash(0.00);
        cards.setNumber(result);
        cardsRepository.save(cards);
    }

    public double findCashByNumber(String number) {
        return cardsRepository.findCashByNumber(number);
    }

    public Optional<Cards> findCardByNumber(String number) {
        return cardsRepository.findByNumber(number);
    }

    public void saveCash(Cards card) {
        cardsRepository.save(card);
    }

    public List<String> getAllCardsAsStrings(String username) {
        Users u = usersService.getUserByLogin(username);
        return cardsRepository.findAllNumbersAsStringsByUser(u);
    }

    public void addImage(Cards card, MultipartFile file)throws IOException {
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + FilenameUtils.removeExtension(file.getOriginalFilename())+".png";

        File temp = new File(uploadPath + "/" + resultFilename);
        card.setFileName(resultFilename);
        //
        file.transferTo(temp);

        resizeImage(temp, imageSize);
    }
    public boolean resizeImage(File sourceFile,Integer size) {
        try {
            BufferedImage bufferedImage = ImageIO.read(sourceFile);
            BufferedImage outputImage = Scalr.resize(bufferedImage, size);
            String newFileName = FilenameUtils.getBaseName(sourceFile.getName())
                    + "_" + size.toString() + ".png";
            System.out.println(FilenameUtils.getBaseName(sourceFile.getName()));
            Path path = Paths.get(uploadPath,newFileName);
            File newImageFile = path.toFile();

            ImageIO.write(outputImage, "png", newImageFile);
            outputImage.flush();
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public List<Cards> getAllCardsFromBank() {
        return cardsRepository.findAll();
    }

    public void updateCash(Long id, Double cash) {
        if (cardsRepository.findById(id).isPresent()) {
            Cards c = cardsRepository.findById(id).get();
            c.setCash(c.getCash() + cash);
            cardsRepository.save(c);
        }
    }
}
