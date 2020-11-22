package be.dog.d.steven.readinglist.controller;

import be.dog.d.steven.readinglist.dao.ReadingListDao;
import be.dog.d.steven.readinglist.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ReadingListController {

    private final ReadingListDao readingListDao;

    @Autowired
    public ReadingListController(ReadingListDao readingListDao) {
        this.readingListDao = readingListDao;
    }

    @GetMapping(value = "/{reader}")
    public String readersBooks(@PathVariable("reader") String reader, Model model){
        List<Book> readingList = readingListDao.findByReader(reader);
        if(readingList != null){
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }

    @PostMapping(value = "/{reader}")
    public String addToReadingList(@PathVariable("reader") String reader, Book book){
        book.setReader(reader);
        readingListDao.save(book);

        return "redirect:/{reader}";
    }

}
