package be.dog.d.steven.readinglist.controller;

import be.dog.d.steven.readinglist.configuration.AmazonProperties;
import be.dog.d.steven.readinglist.dao.ReadingListDao;
import be.dog.d.steven.readinglist.model.Book;
import be.dog.d.steven.readinglist.security.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ReadingListController {

    private final ReadingListDao readingListDao;
    private final AmazonProperties amazonProperties;

    @Autowired
    public ReadingListController(ReadingListDao readingListDao,
                                 AmazonProperties amazonProperties) {
        this.readingListDao = readingListDao;
        this.amazonProperties = amazonProperties;
    }

    @GetMapping
    public String readersBooks(Reader reader, Model model) {
        List<Book> readingList = readingListDao.findByReader(reader.getUsername());
        if (readingList != null) {
            model.addAttribute("books", readingList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonID", amazonProperties.getAssociateId());
        }
        return "readingList";
    }

    @PostMapping
    public String addToReadingList(Reader reader, Book book) {
        book.setReader(reader.getUsername());
        readingListDao.save(book);
        return "redirect:/";
    }
}
