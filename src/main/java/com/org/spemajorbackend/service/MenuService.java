package com.org.spemajorbackend.service;

import com.org.spemajorbackend.dro.AddMenuRequest;
import com.org.spemajorbackend.entity.Menu;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.repository.MenuRepository;
import com.org.spemajorbackend.repository.MessRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MessRepository messRepository;
    private final MenuRepository menuRepository;

    public MenuService(MessRepository messRepository, MenuRepository menuRepository) {
        this.messRepository = messRepository;
        this.menuRepository = menuRepository;
    }

    public boolean addMenuItems(List<AddMenuRequest> menuItems, String mess_owner_username) {
        Mess mess = messRepository.findById(mess_owner_username).orElseThrow(
                () -> new UsernameNotFoundException("Cannot find the mess owned by"+mess_owner_username)
        );
        try{
            for (AddMenuRequest menuItem : menuItems) {
                Menu menu = new Menu(
                        menuItem.getDay(),
                        menuItem.getBreakfast(),
                        menuItem.getLunch(),
                        menuItem.getDinner()
                );
                menu.setMess(mess);
                menuRepository.save(menu);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
