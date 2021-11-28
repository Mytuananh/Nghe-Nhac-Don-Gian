package anhtuan.controller;

import anhtuan.model.Music;
import anhtuan.model.MusicForm;
import anhtuan.service.MusicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/music")
@PropertySource("classpath:upload_file.properties")
public class MusicController {
    private final MusicService musicService = new MusicService();
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
        Music music = new Music(musicForm.getId(), musicForm.getName(),musicForm.getSingle(),musicForm.getCategory(),fileName);
        musicService.save(music);
        musicService.saveForm(musicForm);
        model.addAttribute("musicForm", musicForm);
        redirectAttributes.addFlashAttribute("success", "Create new song successfully!");
        return "redirect:/music";
    }

    @GetMapping("/{id}/delete")
    public String remove(@PathVariable int id){
        musicService.delete(id);
        return "redirect:/music";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable String name) {
        MusicForm musicForm = musicService.findByFormName(name);
        model.addAttribute("musicForm", musicForm);
        return "/edit";
    }
    @PostMapping("/update")
    public String update(Model model, MusicForm musicForm) {
        MultipartFile multipartFile = musicForm.getAudio();
        String fileName= multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(musicForm.getAudio().getBytes(),new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Music music =musicService.findById(musicForm.getId());
        music.setName(musicForm.getName());
        music.setSingle(musicForm.getSingle());
        music.setCategory(musicForm.getCategory());
        music.setAudio(fileName);
        musicService.update(music);
        MusicForm musicForm1 = musicService.findByFormId(musicForm.getId());
        musicForm1.setName(music.getName());
        musicForm1.setSingle(music.getSingle());
        musicForm1.setCategory(music.getCategory());
//        musicForm1.setUpload(multipartFile);
        return "redirect:/music";

    }
}
