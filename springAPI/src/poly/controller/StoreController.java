package poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import poly.controller.comm.AbstractController;
import poly.dto.StoreDTO;
import poly.service.IStoreService;
import poly.util.CmmUtil;
import poly.util.EncryptUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StoreController extends AbstractController {
    @Resource(name = "StoreService")
    private IStoreService storeService;

    @ResponseBody
    @RequestMapping(value = "springAPI/store/insertStoreInfo", produces = "application/json; charset=utf8")
    public Map<String, Object> insertStoreInfo(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".springAPI insertStore Start!");
        Map<String, Object> rMap = new HashMap<>();

        StoreDTO pDTO = new StoreDTO();


        // model 에 넣어서 결과 확인
        try {
            log.info("start!");
            String store_id = CmmUtil.nvl(EncryptUtil.decAES128CBC(request.getParameter("store_id")));// 인코딩해서 넘어온 값 풀기
            log.info("store_id : " + store_id);
            String store_password = CmmUtil.nvl(request.getParameter("store_password"));
            log.info("store_password : " + store_password);
            String store_address = CmmUtil.nvl(EncryptUtil.decAES128CBC(request.getParameter("store_address")));
            log.info("store_address : " + store_address);
            String store_address2 = CmmUtil.nvl(EncryptUtil.decAES128CBC(request.getParameter("store_address2")));

            log.info("store_address2 : " + store_address2);

            pDTO.setStore_id(store_id);
            pDTO.setStore_password(store_password);
            pDTO.setStore_address(store_address);
            pDTO.setStore_address2(store_address2);
            int res = storeService.insertStoreInfo(pDTO);

            log.info("res : " + res);

            rMap.put("store_id", store_id);
            rMap.put("res", res);
        } catch (Exception e) {
            log.info("Exception : " + e);
        }

        return rMap;
    }
}