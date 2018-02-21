package com.sistema.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.brewer.model.Cerveja;
import com.sistema.brewer.repository.helper.cerveja.CervejasQueries;

@Repository
public interface Cervejas  extends JpaRepository<Cerveja, Long>, CervejasQueries{


}
