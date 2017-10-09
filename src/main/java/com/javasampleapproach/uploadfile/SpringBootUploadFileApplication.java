package com.javasampleapproach.uploadfile;


import com.javasampleapproach.uploadfile.controller.UploadController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.nio.file.*;

@SpringBootApplication
public class SpringBootUploadFileApplication implements CommandLineRunner{

	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootUploadFileApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//System.out.println(System.getProperty("user.dir"));
		Path faxFolder = Paths.get("src/upload-dir/");
		WatchService watchService = FileSystems.getDefault().newWatchService();
		faxFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		boolean valid = true;
		do {
			WatchKey watchKey = watchService.take();

			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileName = event.context().toString();
					if (fileName != ".DS_Store"){
						System.out.println("File Created:" + fileName);
						UploadController.filename = fileName;
					}
				}
			}
			valid = watchKey.reset();

		} while (valid);
	}

}
