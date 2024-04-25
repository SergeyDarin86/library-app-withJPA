package ru.library.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.library.springcourse.models.Person;
import ru.library.springcourse.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> allPeople(){
        return peopleRepository.findAll();
    }

    public Person show(int personId){
        return peopleRepository.findById(personId).orElse(null);
    }

    public Optional<Person> show(String fullName){
        return peopleRepository.findPersonByFullName(fullName);
    }

    @Transactional
    public void save (Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setPersonId(id);
        peopleRepository.saveAndFlush(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public Optional<Person>findPersonByBookId(Integer bookId){
        return peopleRepository.findPersonByBookId(bookId);
    }

}
