package anhtuan.controller;

import anhtuan.model.Music;
import anhtuan.model.MusicForm;
import anhtuan.service.IMusicService;
import anhtuan.service.MusicService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/music")
@PropertySource("classpath:upload_file.properties")
public class MusicController {
    @Autowired
    private IMusicService musicService;
    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping("")
    public String index(Model model) {
        List<Music> musicList = musicService.findAll();
        model.addAttribute("musics", musicList);
        return "/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("musicForm", new MusicForm());
        return "/create";
    }

    @PostMapping("/save")
    public String save(Model model, MusicForm musicForm, RedirectAttributes redirectAttributes) {
        MultipartFile multipartFile = musicForm.getAudio();
        String fileName= multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(musicForm.getAudio().getBytes(),new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Music music = new Music(musicForm.getName(),musicForm.getSingle(),musicForm.getCategory(),fileName);
        musicService.save(music);
//        musicService.saveForm(musicForm);
        model.addAttribute("musicForm", musicForm);
        redirectAttributes.addFlashAttribute("success", "Create new song successfully!");
        return "redirect:/music";
    }

    @GetMapping("/{id}/delete")
    public String remove(@PathVariable int id){
        musicService.delete(id);
        return "redirect:/music";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable int id) throws IOException {
        Music music = musicService.findOne(id);
        File file = new File(fileUpload + music.getAudio());
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("fileNew",file.getName(),"mp3,mp4,.../audio", IOUtils.toByteArray(inputStream));
        MusicForm musicForm = new MusicForm(music.getId(), music.getName(),music.getSingle(),music.getCategory(),multipartFile);
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("musicForm", musicForm);
        return modelAndView;
    }
    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute MusicForm musicForm) {
        MultipartFile multipartFile = musicForm.getAudio();
        String fileName= multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(musicForm.getAudio().getBytes(),new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
            Music music = new Music(musicForm.getId(), musicForm.getName(), musicForm.getSingle(), musicForm.getCategory(), fileName);
            musicService.update(music);
            ModelAndView modelAndView = new ModelAndView("redirect:/music");
            modelAndView.addObject("musicForm", musicForm);
            return modelAndView;


//        Music music =musicService.findById(musicForm.getId());
//        music.setName(musicForm.getName());
//        music.setSingle(musicForm.getSingle());
//        music.setCategory(musicForm.getCategory());
//        music.setAudio(fileName);
//        musicService.update(music);
//        MusicForm musicForm1 = musicService.findByFormId(musicForm.getId());
//        musicForm1.setName(music.getName());
//        musicForm1.setSingle(music.getSingle());
//        musicForm1.setCategory(music.getCategory());
////        musicForm1.setUpload(multipartFile);


    }
}
