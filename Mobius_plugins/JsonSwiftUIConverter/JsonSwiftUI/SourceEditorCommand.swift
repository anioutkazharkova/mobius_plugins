//
//  SourceEditorCommand.swift
//  JsonSwiftUI
//
//  Created by Anna Zharkova on 14.05.2024.
//

import Foundation
import XcodeKit
import AppKit

class SourceEditorCommand: NSObject, XCSourceEditorCommand {
    
    func perform(with invocation: XCSourceEditorCommandInvocation, completionHandler: @escaping (Error?) -> Void ) -> Void {
        // Implement your command here, invoking the completion handler when done. Pass it nil on success, and an NSError on failure.
        guard let json = NSPasteboard.general.string(forType: .string) else {
            return
        }
        let lines = invocation.buffer.lines
       // let newLines = JsonSwiftUIGenerator.printView(json: json.data(using: .utf8) ?? Data())
            //lines.add(newLines)
        completionHandler(nil)
    }
    
}
