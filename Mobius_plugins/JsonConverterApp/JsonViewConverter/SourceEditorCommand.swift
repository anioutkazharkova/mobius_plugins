//
//  SourceEditorCommand.swift
//  JsonViewConverter
//
//  Created by Anna Zharkova on 29.02.2024.
//

import Foundation
import XcodeKit
import AppKit

class CustomError : Error {
    let message: String
    
    init(message: String) {
        self.message = message
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

class SourceEditorCommand: NSObject, XCSourceEditorCommand {
    
    func perform(with invocation: XCSourceEditorCommandInvocation, completionHandler: @escaping (Error?) -> Void ) -> Void {
        // Implement your command here, invoking the completion handler when done. Pass it nil on success, and an NSError on failure.
        guard let json = NSPasteboard.general.string(forType: .string) else {
            completionHandler(CustomError(message: "Couldn't get JSON from clipboard"))
            return
        }
        let lines = invocation.buffer.lines
        //lines.add("\n" + json)
        
        let range = invocation.buffer.selections.firstObject as! XCSourceTextRange
        // match clipped text
        let match = xTextMatcher.match(selection: range, invocation: invocation, options: .selected)

        let endLineIndex = range.end.line + 2
        let jsonstr = json//match.text
        
      /* let baseClass =  invocation.commandIdentifier
       let model = ModelFactory(base:baseClass,name:"<#NewModelName#>")
        let modelCode  = model.genModel(src: jsonstr)
        invocation.buffer.lines.insert(modelCode, at: endLineIndex)*/
        do {try convertJSONToSwift(invocation.buffer, jsonString: jsonstr)
        }
        catch {
            print(error)
        }
        completionHandler(nil)
    }
    
    func convertJSONToSwift(_ buffer: XCSourceTextBuffer, jsonString: String) throws {
        //let range = buffer.selections.firstObject as! XCSourceTextRange
        
        /*var jsonString = ""
        for index in range.start.line..<range.end.line {
            jsonString += buffer.lines[index] as! String
        }*/
        guard !jsonString.isEmpty else { return}
        let lineIndent = LineIndent(useTabs: buffer.usesTabsForIndentation, indentationWidth: buffer.indentationWidth, level: 1)
        if let property = JSONProperty(from: jsonString) {
            let output = property.generateOutput(lineIndent: lineIndent)
            buffer.lines.add(output)
        }
    }
    
    func outputResult(_ property: JSONProperty, to buffer: XCSourceTextBuffer, in range: XCSourceTextRange) {
        // Remove the current lines
        buffer.lines.removeObjects(in: NSRange(location: range.start.line, length: range.end.line - range.start.line))
        
        // Generate the new lines
        let lineIndent = LineIndent(useTabs: buffer.usesTabsForIndentation, indentationWidth: buffer.indentationWidth, level: 1)
        let output = property.generateOutput(lineIndent: lineIndent)
        
        // Insert the new lines
        let lineCount = buffer.lines.count
        buffer.lines.insert(output, at: range.start.line)
        let insertedLineCount = buffer.lines.count - lineCount
        
        // Update the selection
        let selection = XCSourceTextRange(start: XCSourceTextPosition(line: range.start.line, column: 0), end: XCSourceTextPosition(line: range.start.line + insertedLineCount, column: 0))
        buffer.selections.removeAllObjects()
        buffer.selections.insert(selection, at: 0)
    }
    
}

