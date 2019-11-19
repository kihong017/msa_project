package springboot.microservice.restdemo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextController {
	
	private Map<Long, Text> textData = new HashMap<Long, Text>();
	private Long nextId = (long) 0;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String test() {
	    
		return textData.toString();
		
    }
	
	@RequestMapping(value = "/text/{id}", method = RequestMethod.GET)
	public Text detail(@PathVariable("id") Long id) {

		return textData.get(id);
	}

	
//  Need to Change the RequestParam to RequestBody 	
	@RequestMapping(value="/text", method= {RequestMethod.POST})
	public String addText(@RequestParam(value="title")   String title
						   , @RequestParam(value="content") String content
						   , @RequestParam(value="status")  String status
						   , @RequestParam(value="author")  String author) {
		
		Text newText = new Text();
		
		newText.setId(getNextId());
		newText.setTitle(title);
		newText.setContent(content);
		newText.setStatus(status);
		newText.setAuthor(author);
		
		textData.put(newText.getId(), newText);
		
		return "Add Successful";
	}

//  Need to Change the RequestParam to RequestBody
	@RequestMapping(value="/text/{id}", method= {RequestMethod.PUT})
	public Text modifyText(@PathVariable("id")            Long   id
						 , @RequestParam(value="title")   String title
						 , @RequestParam(value="content") String content
						 , @RequestParam(value="status")  String status
						 , @RequestParam(value="author")  String author) {
		
		Text modifyingText = textData.get(id);
		
		modifyingText.setTitle  (null != title ? title : modifyingText.getTitle());
		modifyingText.setContent(null != content ? content : modifyingText.getContent());
		modifyingText.setStatus (null != status ? status : modifyingText.getStatus());
		modifyingText.setAuthor (null != author ? author : modifyingText.getAuthor());
		
		textData.put(id, modifyingText);
		
		return modifyingText;
	}
	
	@RequestMapping(value="/text/{id}", method=RequestMethod.DELETE)
	public String deleteText(@PathVariable("id") Long id) {

		Text deleteText = textData.get(id);
		
		if (null != deleteText) {
			
			if (deleteText.getStatus().equals("publish")) {
				deleteText.setStatus("trash");
				textData.put(id, deleteText);
			} else {
				textData.remove(id);
			}
			
		}
		
		return "Delete Successful";
		
	}
	
	
	@RequestMapping(value = "test1", method = RequestMethod.GET)
	public Text test1() {
	    
		Text helloWorld = new Text();
		helloWorld.setId(0);
		helloWorld.setTitle("test1");
		
	    return helloWorld;
    }
	
	@RequestMapping(value = "test2", method = RequestMethod.GET)
	public Text test2() {

		Text helloWorld = new Text();
		helloWorld.setId(0);
		helloWorld.setTitle("test2");
	    
	    return helloWorld;
    }
	
	public Long getNextId() {
		
		return nextId++;		
		
	}

}
