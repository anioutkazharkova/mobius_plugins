//
//  SourceEditorCommand.swift
//  JsonSwiftUIExt
//
//  Created by Anna Zharkova on 12.05.2024.
//

import Foundation
import XcodeKit

class SourceEditorCommand: NSObject, XCSourceEditorCommand {
    
    func perform(with invocation: XCSourceEditorCommandInvocation, completionHandler: @escaping (Error?) -> Void ) -> Void {
        // Implement your command here, invoking the completion handler when done. Pass it nil on success, and an NSError on failure.
        
        let lines = invocation.buffer.lines
                // Reverse the order of the lines in a copy.
                let updatedText = Array(lines.reversed())
                lines.removeAllObjects()
                lines.addObjects(from: updatedText)
                // Signal to Xcode that the command has completed.
                completionHandler(nil)
    }
    
}
