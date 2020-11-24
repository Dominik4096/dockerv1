/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab5.controllers;

import com.mycompany.lab5.beans.Pracownik;
import com.mycompany.lab5.dao.PracownikDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Dominik
 */
@Controller
public class PracownikController {

    @Autowired
    PracownikDao dao; //wstrzyknięcie dao z pliku XML

    /* Wynikiem działania metody jest wyświetlenie formularza do wprowadzania
danych, a „command” jest zastrzeżonym atrybutem żądania, który służy do
wyświetlania danych obiektu w formularzu.
     */
    @RequestMapping("/addForm")
    public String showform(Model m) {
        m.addAttribute("command", new Pracownik());
        return "addForm";
    }

    /* Metoda obsługuje zapis pracownika do BD. @ModelAttribute umieszcza dane
 * z żądania w obiekcie modelu. Jawnie wskazano tu metodę RequestMethod.POST,
 * ponieważ domyślnie jest to metoda GET
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("pr") Pracownik pr) {
        dao.save(pr);
        return "redirect:/viewAll";//przekierowanie do widoku /viewAll
    }
    /* Metoda pobiera listę pracowników z BD i umieszcza je w modelu */
    @RequestMapping("/viewAll")
    public String viewAll(Model m) {
        List<Pracownik> list = dao.getAll();
        m.addAttribute("list", list);
        return "viewAll";
    }
     @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        dao.delete(id);
        return "redirect:/viewAll";
    }
      @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable int id, Model model) {
        Pracownik pracownik = dao.getPracownikById(id);
        model.addAttribute("command",pracownik);
        return "editForm";
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editSave(@ModelAttribute("pr")Pracownik pr) {
        dao.update(pr);
        return "redirect:/viewAll";
    }
}
