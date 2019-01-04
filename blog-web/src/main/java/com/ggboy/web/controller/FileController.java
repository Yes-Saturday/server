package com.ggboy.web.controller;

import com.ggboy.common.constant.PropertiesConstant;
import com.ggboy.common.convert.SequenceIdConvert;
import com.ggboy.common.domain.FrontEndResponse;
import com.ggboy.common.exception._404Exception;
import com.ggboy.common.utils.IoUtil;
import com.ggboy.common.utils.StringUtil;
import com.ggboy.system.enums.SequenceName;
import com.ggboy.system.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private SequenceService sequenceService;

    @PostMapping("/uploadImg")
    @ResponseBody
    public FrontEndResponse uploadImg(MultipartFile file, HttpServletRequest request) {
        try {
            if (file == null)
                return FrontEndResponse.fail("error", "file is not exits");
            String fileName = upload(file, PropertiesConstant.getDefaultFilePathImg());
            var scheme = request.getHeader("x-forwarded-scheme");
            scheme = StringUtil.isEmpty(scheme) ? request.getScheme() : scheme;
            return FrontEndResponse.success(scheme + "://" + request.getServerName() + "/images/" + fileName);
        } catch (IOException e) {
            return FrontEndResponse.fail("error", e.getMessage());
        }
    }

    public String upload(MultipartFile file, String filePath) throws IOException {
        int suffixIndex = file.getOriginalFilename().lastIndexOf(".");
        String suffix = suffixIndex > 0 ? file.getOriginalFilename().substring(suffixIndex) : "";
        var number = sequenceService.next(SequenceName.FileName);
        String fileName = SequenceIdConvert.convert("file_", number, 11) + suffix;
        File dir = new File(filePath);
        if (!dir.exists())
            dir.mkdirs();
        file.transferTo(new File(filePath + File.separator + fileName));
        return fileName;
    }
}