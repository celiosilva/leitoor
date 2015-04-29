package br.com.delogic.leitoor.entidade.mapped;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import br.com.delogic.jfunk.data.Identity;
import br.com.delogic.leitoor.entidade.Professor;

@MappedSuperclass
public abstract class DominioProfessor<E> extends Identity<E> {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Professor professor;

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

}
