package exercise;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;

class App {

    // BEGIN
    public static CompletableFuture<String> unionFiles(String src1, String src2, String dest) {
        // reading first part
        CompletableFuture<String> file1 = CompletableFuture.supplyAsync(() -> {
            String content = null;
            Path path = Paths.get(src1).toAbsolutePath().normalize();

            try {
                content = Files.readString(path);
            } catch (IOException e) {
                throw new RuntimeException(src1);
            }
            return content;
        });

        // reading second part
        CompletableFuture<String> file2 = CompletableFuture.supplyAsync(() -> {
            String content = null;
            Path path = Paths.get(src2).toAbsolutePath().normalize();

            try {
                content = Files.readString(path);
            } catch (IOException e) {
                throw new RuntimeException(src2);
            }
            return content;
        });

        // combining result
        CompletableFuture<String> unionedFiles = file1.thenCombine(file2, (content1, content2) -> {
            return content1 + content2;
        }).exceptionally(ex -> {
            System.out.println("NoSuchFileException " + ex.getMessage());
            return "";
        });

        // trying to write result
        Path path = Paths.get(dest).toAbsolutePath().normalize();
        try {
            Files.writeString(path, unionedFiles.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        
        return unionedFiles;
    }
    // END

    public static void main(String[] args) throws Exception {
        // BEGIN
        unionFiles("src/main/resources/file1.txt", "src/main/resources/file2.txt",
                "src/main/resources/dest.txt");
        // END
    }
}

