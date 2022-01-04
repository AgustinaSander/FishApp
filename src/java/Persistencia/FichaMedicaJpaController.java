/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Habitante;
import Logica.Enfermedad;
import Logica.FichaMedica;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Agustina
 */
public class FichaMedicaJpaController implements Serializable {

    public FichaMedicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public FichaMedicaJpaController(){
        emf = Persistence.createEntityManagerFactory("FishAppPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FichaMedica fichaMedica) {
        if (fichaMedica.getListaEnfermedades() == null) {
            fichaMedica.setListaEnfermedades(new ArrayList<Enfermedad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Habitante habitante = fichaMedica.getHabitante();
            if (habitante != null) {
                habitante = em.getReference(habitante.getClass(), habitante.getIdHabitante());
                fichaMedica.setHabitante(habitante);
            }
            List<Enfermedad> attachedListaEnfermedades = new ArrayList<Enfermedad>();
            for (Enfermedad listaEnfermedadesEnfermedadToAttach : fichaMedica.getListaEnfermedades()) {
                listaEnfermedadesEnfermedadToAttach = em.getReference(listaEnfermedadesEnfermedadToAttach.getClass(), listaEnfermedadesEnfermedadToAttach.getIdEnfermedad());
                attachedListaEnfermedades.add(listaEnfermedadesEnfermedadToAttach);
            }
            fichaMedica.setListaEnfermedades(attachedListaEnfermedades);
            em.persist(fichaMedica);
            if (habitante != null) {
                FichaMedica oldFichaOfHabitante = habitante.getFicha();
                if (oldFichaOfHabitante != null) {
                    oldFichaOfHabitante.setHabitante(null);
                    oldFichaOfHabitante = em.merge(oldFichaOfHabitante);
                }
                habitante.setFicha(fichaMedica);
                habitante = em.merge(habitante);
            }
            for (Enfermedad listaEnfermedadesEnfermedad : fichaMedica.getListaEnfermedades()) {
                FichaMedica oldFichaOfListaEnfermedadesEnfermedad = listaEnfermedadesEnfermedad.getFicha();
                listaEnfermedadesEnfermedad.setFicha(fichaMedica);
                listaEnfermedadesEnfermedad = em.merge(listaEnfermedadesEnfermedad);
                if (oldFichaOfListaEnfermedadesEnfermedad != null) {
                    oldFichaOfListaEnfermedadesEnfermedad.getListaEnfermedades().remove(listaEnfermedadesEnfermedad);
                    oldFichaOfListaEnfermedadesEnfermedad = em.merge(oldFichaOfListaEnfermedadesEnfermedad);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FichaMedica fichaMedica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichaMedica persistentFichaMedica = em.find(FichaMedica.class, fichaMedica.getIdFicha());
            Habitante habitanteOld = persistentFichaMedica.getHabitante();
            Habitante habitanteNew = fichaMedica.getHabitante();
            List<Enfermedad> listaEnfermedadesOld = persistentFichaMedica.getListaEnfermedades();
            List<Enfermedad> listaEnfermedadesNew = fichaMedica.getListaEnfermedades();
            if (habitanteNew != null) {
                habitanteNew = em.getReference(habitanteNew.getClass(), habitanteNew.getIdHabitante());
                fichaMedica.setHabitante(habitanteNew);
            }
            List<Enfermedad> attachedListaEnfermedadesNew = new ArrayList<Enfermedad>();
            for (Enfermedad listaEnfermedadesNewEnfermedadToAttach : listaEnfermedadesNew) {
                listaEnfermedadesNewEnfermedadToAttach = em.getReference(listaEnfermedadesNewEnfermedadToAttach.getClass(), listaEnfermedadesNewEnfermedadToAttach.getIdEnfermedad());
                attachedListaEnfermedadesNew.add(listaEnfermedadesNewEnfermedadToAttach);
            }
            listaEnfermedadesNew = attachedListaEnfermedadesNew;
            fichaMedica.setListaEnfermedades(listaEnfermedadesNew);
            fichaMedica = em.merge(fichaMedica);
            if (habitanteOld != null && !habitanteOld.equals(habitanteNew)) {
                habitanteOld.setFicha(null);
                habitanteOld = em.merge(habitanteOld);
            }
            if (habitanteNew != null && !habitanteNew.equals(habitanteOld)) {
                FichaMedica oldFichaOfHabitante = habitanteNew.getFicha();
                if (oldFichaOfHabitante != null) {
                    oldFichaOfHabitante.setHabitante(null);
                    oldFichaOfHabitante = em.merge(oldFichaOfHabitante);
                }
                habitanteNew.setFicha(fichaMedica);
                habitanteNew = em.merge(habitanteNew);
            }
            for (Enfermedad listaEnfermedadesOldEnfermedad : listaEnfermedadesOld) {
                if (!listaEnfermedadesNew.contains(listaEnfermedadesOldEnfermedad)) {
                    listaEnfermedadesOldEnfermedad.setFicha(null);
                    listaEnfermedadesOldEnfermedad = em.merge(listaEnfermedadesOldEnfermedad);
                }
            }
            for (Enfermedad listaEnfermedadesNewEnfermedad : listaEnfermedadesNew) {
                if (!listaEnfermedadesOld.contains(listaEnfermedadesNewEnfermedad)) {
                    FichaMedica oldFichaOfListaEnfermedadesNewEnfermedad = listaEnfermedadesNewEnfermedad.getFicha();
                    listaEnfermedadesNewEnfermedad.setFicha(fichaMedica);
                    listaEnfermedadesNewEnfermedad = em.merge(listaEnfermedadesNewEnfermedad);
                    if (oldFichaOfListaEnfermedadesNewEnfermedad != null && !oldFichaOfListaEnfermedadesNewEnfermedad.equals(fichaMedica)) {
                        oldFichaOfListaEnfermedadesNewEnfermedad.getListaEnfermedades().remove(listaEnfermedadesNewEnfermedad);
                        oldFichaOfListaEnfermedadesNewEnfermedad = em.merge(oldFichaOfListaEnfermedadesNewEnfermedad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = fichaMedica.getIdFicha();
                if (findFichaMedica(id) == null) {
                    throw new NonexistentEntityException("The fichaMedica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichaMedica fichaMedica;
            try {
                fichaMedica = em.getReference(FichaMedica.class, id);
                fichaMedica.getIdFicha();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichaMedica with id " + id + " no longer exists.", enfe);
            }
            Habitante habitante = fichaMedica.getHabitante();
            if (habitante != null) {
                habitante.setFicha(null);
                habitante = em.merge(habitante);
            }
            List<Enfermedad> listaEnfermedades = fichaMedica.getListaEnfermedades();
            for (Enfermedad listaEnfermedadesEnfermedad : listaEnfermedades) {
                listaEnfermedadesEnfermedad.setFicha(null);
                listaEnfermedadesEnfermedad = em.merge(listaEnfermedadesEnfermedad);
            }
            em.remove(fichaMedica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FichaMedica> findFichaMedicaEntities() {
        return findFichaMedicaEntities(true, -1, -1);
    }

    public List<FichaMedica> findFichaMedicaEntities(int maxResults, int firstResult) {
        return findFichaMedicaEntities(false, maxResults, firstResult);
    }

    private List<FichaMedica> findFichaMedicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FichaMedica.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FichaMedica findFichaMedica(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichaMedica.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichaMedicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichaMedica> rt = cq.from(FichaMedica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
