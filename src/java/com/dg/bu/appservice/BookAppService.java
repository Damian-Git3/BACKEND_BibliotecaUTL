package com.dg.bu.appservice;

import java.util.ArrayList;
import java.util.List;

import com.dg.bu.cqrs.BookCqrs;
import com.dg.bu.dao.BookDao;
import com.dg.bu.model.Book;
import com.dg.bu.viewmodel.BookViewModel;

public class BookAppService {

  private BookCqrs bookCqrs = new BookCqrs();
  private BookDao bookDao = new BookDao();

  public Book register(Book book) {
    // Inserta el usuario utilizando UsuarioCQRS
    return bookCqrs.registerBook(book);
  }

  public Book update(Book book) {
    // Inserta el usuario utilizando UsuarioCQRS
    return bookCqrs.updateBook(book);
  }

  public void delete(Book book) {
    // Inserta el usuario utilizando UsuarioCQRS
    bookCqrs.deleteBook(book.getIdBook());
  }

  public Book findBook(int id) {
    Book book = bookDao.getBookById(id);
    return book;
  }

  public BookViewModel findBookPublic(int idBook) {
    BookViewModel bookViewModel = new BookViewModel();

    Book book = bookDao.getBookById(idBook);

    bookViewModel.setId(book.getIdBook().intValue());
    bookViewModel.setTitulo(book.getName());
    bookViewModel.setCreador(book.getAuthor());
    bookViewModel.setOrigen(book.getUniversity());
    bookViewModel.setArchivoDoc(book.getLibroBase64());

    return bookViewModel;
  }

  public List<BookViewModel> findBooksPublic() {
    List<BookViewModel> bookViewModels = new ArrayList<>();

    List<Book> books = bookDao.getAllBooks();

    for (Book book : books) {
      BookViewModel bookViewModel = new BookViewModel();
      bookViewModel.setId(book.getIdBook().intValue());
      bookViewModel.setTitulo(book.getName());
      bookViewModel.setCreador(book.getAuthor());
      bookViewModel.setOrigen(book.getUniversity());
      bookViewModel.setArchivoDoc(book.getLibroBase64());
      bookViewModels.add(bookViewModel);
    }

    return bookViewModels;
  }

}
