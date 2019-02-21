package com.saturday.web.controller;

import com.saturday.common.constant.PropertiesConstant;
import com.saturday.common.convert.SequenceIdConvert;
import com.saturday.common.domain.FrontEndResponse;
import com.saturday.common.utils.StringUtil;
import com.saturday.sequence.enums.SequenceName;
import com.saturday.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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