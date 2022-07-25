package es.jmrv.service;

import es.jmrv.dto.PersonDto;
import es.jmrv.model.BalanceUpdater;
import es.jmrv.model.Duty;
import es.jmrv.model.DutyBuilder;
import es.jmrv.model.Person;
import es.jmrv.repository.PersonRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
public class PersonService {
    @Inject
    private PersonRepository personRepository;

    @Inject
    private DutyBuilder dutyBuilder;

    @Inject
    private BalanceUpdater balanceUpdater;

    public List<PersonDto> findAllDto(){
        return this.findAll().stream().map(p -> new PersonDto(p.getId(), p.getName(), p.getBalance())).collect(Collectors.toList());
    }

    public Optional<Person> findById(Long id){
        return this.personRepository.findById(id);
    }

    public List<Duty> calculateDuties() {
        return dutyBuilder.build(this.findAll());
    }

    public Person save(Person person) {
        return this.personRepository.save(person);
    }

    @Transactional
    public void updateBalances() {
        List<Person> personList = this.findAll();
        this.balanceUpdater.updateBalance(personList);
        this.personRepository.updateAll(personList);
    }

    public void delete(Person person) {
        this.personRepository.delete(person);
    }

    private List<Person> findAll() {
        return StreamSupport.stream(this.personRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
