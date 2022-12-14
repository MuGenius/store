package com.my.store.controller;

import com.my.store.entity.Address;
import com.my.store.service.IAddressService;
import com.my.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController extends BaseController {

    @Autowired
    private IAddressService addressService;

    @RequestMapping("/add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid, username, address);
        return new JsonResult<>(OK);
    }

    @RequestMapping({"", "/"})
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> data = addressService.getAddressByUid(uid);
        return new JsonResult<>(OK, data);
    }

    // Restful风格请求
    @RequestMapping("/{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid,
                                       HttpSession session) {
        addressService.setDefault(
                aid,
                getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping("/{aid}/delete")
    public JsonResult<Void> deleteAddress(
            @PathVariable("aid") Integer aid,
            HttpSession session) {
        addressService.deleteAddress(
                aid,
                getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }
}
